package com.deviseworks.mccBackend.controller

import com.deviseworks.mccBackend.domain.Player
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class PlayerController {

    val onlineList = mutableListOf<Player>()


    @GetMapping("/api/player/list")
    @ResponseBody
    fun getPlayerList(): String{
        // とりあえずonlineList返しとくか
        return onlineList.toString()
    }

    @PostMapping("/api/player/list/{status}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updatePlayerList(
        @PathVariable status: String,
        @RequestBody body: String
    ){
        println(body)
        // 受け取ったBodyをJSONにパースして取得したユーザをonlineListに追加する
        val player = Player().parse(body)?:return

        when(status){
            "join" ->{
                onlineList.add(player)
            }
            "quit" ->{
                onlineList.mapIndexed { i, p ->
                    if(p.uuid == player.uuid){
                        onlineList.removeAt(i)
                        return
                    }
                }
            }
        }
    }
}