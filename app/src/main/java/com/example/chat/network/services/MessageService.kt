package com.example.chat.network.services

import com.example.chat.models.MessageData

interface MessageService {
    suspend fun getAllMsg():List<MessageData>

    companion object{
        const val BASE_URL="http://10.0.2.2:8080"
    }

    sealed class ApiUrl(val url:String){
        object MessageApi: ApiUrl("$BASE_URL$/message")
    }
}