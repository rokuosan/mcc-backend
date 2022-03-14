package com.deviseworks.mccBackend.controller

import com.deviseworks.mccBackend.domain.Memory
import com.deviseworks.mccBackend.domain.Order
import com.deviseworks.mccBackend.domain.PreOrder
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat
import java.util.*

@RestController
@CrossOrigin
class ServerController {
    // Server Orders
    val orderList = mutableListOf<Order>()

    // Server Memory
    var serverMemory = Memory()

    @GetMapping("/api/server/memory")
    @ResponseBody
    fun getServerMemory(): String{
        return Json.encodeToString(Memory.serializer(), serverMemory)
    }

    @PostMapping("/api/server/memory")
    fun updateServerMemory(@RequestBody body: String){
        val json = Memory().parse(body)?: return
        serverMemory=json
    }

    @GetMapping("/api/server/order")
    @ResponseBody
    fun getOrder(
        @RequestParam(required = false) done: Boolean=false,
        @RequestParam(required = false) cancel: Boolean=false,
        @RequestParam(required = false) all: Boolean=true
    ): String{
        // done, cancelのフィルタがある場合はdoneを優先する
        return if(done){
            Json.encodeToString(orderList.filter { it.isDone==true })
        }else if(cancel){
            Json.encodeToString(orderList.filter { it.isCanceled==true })
        }else{
            Json.encodeToString(orderList)
        }
    }

    // /add?command=abc_abc_abc_abc&sender=foo& <- これはカス
    // /add にbodyでJSON形式でコマンド送ろう
    @PostMapping("/api/server/order/add")
    fun addOrder(@RequestBody bodyRaw: String): ResponseEntity<String>{
        val body: PreOrder

        // JSONをデシリアライズ
        try{
            body = Json.decodeFromString(bodyRaw)
        }catch(e: Exception){
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        // 処理
        try{
            val date = Date()

            val order = Order(
                id = orderList.size,
                senderUUID = body.sender,
                command = body.command,
                date = SimpleDateFormat("yyyy-MM-dd").format(date),
                time = SimpleDateFormat("hh:mm:ss").format(date),
                isDone = false,
                isCanceled = false
            )

            // 命令登録
            for(o in orderList){
                if(o.id == order.id){
                    return ResponseEntity(HttpStatus.CONFLICT)
                }
            }
            orderList.add(order)

            return ResponseEntity(Json.encodeToString(order), HttpStatus.CREATED)

        }catch(e: Exception){
            return ResponseEntity(HttpStatus.EXPECTATION_FAILED)
        }
    }
//    fun addOrder(@RequestParam command: String, @RequestParam sender: String): ResponseEntity<String>{
//        // 日付取得
//        val date = Date()
//
//        // 命令作成
//        val order = Order(
//            id = orderList.size,
//            senderUUID = sender,
//            command = command,
//            date = SimpleDateFormat("yyyy-MM-dd").format(date),
//            time = SimpleDateFormat("hh:mm:ss").format(date),
//            isDone = false,
//            isCanceled = false
//        )
//
//        // 命令登録
//        for(o in orderList){
//            if(o.id == order.id){
//                return ResponseEntity(HttpStatus.CONFLICT)
//            }
//        }
//        orderList.add(order)
//
//        return ResponseEntity(Json.encodeToString(order), HttpStatus.CREATED)
//    }

    // /cancel?id=111
    @PostMapping("/api/server/order/cancel")
    fun cancelOrder(@RequestParam id: Int): ResponseEntity<String>{
        orderList.forEachIndexed { i, o ->
            if(o.id == id){
                if(o.isDone!! || o.isCanceled!!) return ResponseEntity(HttpStatus.BAD_REQUEST)
                o.isCanceled = true
                orderList[i] = o
                return ResponseEntity(HttpStatus.OK)
            }
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/api/server/order/done")
    fun doneOrder(@RequestParam id: Int): ResponseEntity<String>{
        orderList.forEachIndexed { i, o ->
            if(o.id == id){
                if(o.isDone!!) return ResponseEntity(HttpStatus.BAD_REQUEST)
                o.isDone = true
                orderList[i] = o
                return ResponseEntity(HttpStatus.OK)
            }
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }
}