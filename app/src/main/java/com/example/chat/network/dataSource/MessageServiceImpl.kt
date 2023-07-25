package com.example.chat.network.dataSource

import com.example.chat.models.MessageData
import com.example.chat.network.services.MessageService
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.get
import java.lang.Exception

class MessageServiceImpl(private val client: HttpClient) : MessageService {
    override suspend fun getAllMsg(): List<MessageData> {
        return try {
            client.get(MessageService.ApiUrl.MessageApi.url).body<List<MessageData>>()
        }catch (e:Exception){
            emptyList()
        }
    }
}