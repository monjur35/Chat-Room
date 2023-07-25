package com.example.chat.network.dataSource

import android.util.Log
import com.example.chat.models.MessageData
import com.example.chat.network.services.MessageService
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.get
import java.lang.Exception
import javax.inject.Inject

class MessageServiceImpl @Inject constructor(private val client: HttpClient) : MessageService {
    override suspend fun getAllMsg(): List<MessageData> {
        return try {
            client.get(MessageService.ApiUrl.ALLMessageApi.url).body<List<MessageData>>()
        }catch (e:Exception){
            Log.e("TAG", "getAllMsg:${e.message} ")
            emptyList()
        }
    }
}