package com.example.chat.models

import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat

import java.util.Date

@Serializable
data class MessageData(
    val id: String,
    val msgText: String,
    val timeStamp: Long,
    val userName: String
){
    val date :Date
        get() = Date(timeStamp)


    val formattedDate=SimpleDateFormat("dd-MM-yyyy").format(date)

}