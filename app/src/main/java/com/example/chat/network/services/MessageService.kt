package com.example.chat.network.services

import com.example.chat.models.MessageData

interface MessageService {
    suspend fun getAllMsg():List<MessageData>







    companion object{
       // const val BASE_URL="http://192.168.0.102:8080"
        const val BASE_URL="http://100.100.100.79:80"
    }

    sealed class ApiUrl(val url:String){
        object ALLMessageApi: ApiUrl("$BASE_URL/messages")
    }
}