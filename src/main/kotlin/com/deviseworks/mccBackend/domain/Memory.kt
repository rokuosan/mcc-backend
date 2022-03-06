package com.deviseworks.mccBackend.domain

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Memory(
    @SerialName("total_memory")
    val totalMemory: Long,
    @SerialName("max_memory")
    val maxMemory: Long,
    @SerialName("free_memory")
    val freeMemory: Long,
    @SerialName("used_memory")
    val usedMemory: Long,
    @SerialName("ratio")
    val ratio: Double
)