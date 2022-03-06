package com.deviseworks.mccBackend.controller

import com.deviseworks.mccBackend.domain.Memory
import kotlinx.serialization.json.Json
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ServerController {

    // Server VM
    val runtime: Runtime = Runtime.getRuntime()

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
}