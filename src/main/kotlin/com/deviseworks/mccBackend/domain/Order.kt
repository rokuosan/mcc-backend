package com.deviseworks.mccBackend.domain

import kotlinx.serialization.SerialName
@kotlinx.serialization.Serializable
data class Order(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("sender_uuid")
    val senderUUID: String? = null,
    @SerialName("date")
    val date: String? = null,
    @SerialName("time")
    val time: String? = null,
    @SerialName("command")
    val command: String? = null,
    @SerialName("is_done")
    var isDone: Boolean? = null,
    @SerialName("is_canceled")
    var isCanceled: Boolean? = null
)


/*
id,
sender,
date, yyyy-MM-dd
time, hh:mm:ss
command,
isDone,
isCanceled
 */