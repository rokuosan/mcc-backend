package com.deviseworks.mccBackend.controller

import com.deviseworks.mccBackend.domain.Memory
import kotlinx.serialization.json.Json
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ServerController {

    // Server VM
    val runtime: Runtime = Runtime.getRuntime()

    @GetMapping("/api/server/memory")
    @ResponseBody
    fun getServerMemory(): String{
        // Get memory size
        val totalKB = runtime.totalMemory() / 1024
        val maxKB = runtime.maxMemory() / 1024
        val freeKB = runtime.freeMemory() / 1024
        val usedKB = totalKB - freeKB
        val ratio: Double = (usedKB / totalKB.toDouble()) * 100

        // Serialize
        val memory = Memory(totalKB, maxKB, freeKB, usedKB, ratio)

        // Encode
        return Json.encodeToString(Memory.serializer(), memory)
    }

    @PostMapping("/api/server/memory")
    fun updateServerMemory(){

    }
}