package com.deviseworks.mccBackend.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@kotlinx.serialization.Serializable
data class Memory(
    @SerialName("total_memory")
    val totalMemory: Long = 0,

    @SerialName("max_memory")
    val maxMemory: Long = 0,

    @SerialName("free_memory")
    val freeMemory: Long = 0,

    @SerialName("used_memory")
    val usedMemory: Long = 0,

    @SerialName("ratio")
    val ratio: Double = 0.0
){
    fun parse(body: String): Memory?{
        return Json.decodeFromString(body)
    }
}