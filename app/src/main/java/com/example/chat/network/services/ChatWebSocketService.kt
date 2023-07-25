package com.example.chat.network.services

import com.example.chat.models.MessageData
import com.example.chat.utils.Resource
import kotlinx.coroutines.flow.Flow


interface ChatWebSocketService {

    suspend fun establishSession(userName:String):Resource<Unit>

    suspend fun sendMsg(message:String)

    fun observeIncomingMsg(): Flow<MessageData>

    suspend fun closeSession()


    companion object{
        const val BASE_URL="ws://100.100.100.79:8080"
    }
    sealed class SocketUrl(val url:String){
        object SocketUrlApi: SocketUrl("${BASE_URL}/chat-socket")
    }
}