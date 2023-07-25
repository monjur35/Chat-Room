package com.example.chat.network.dataSource

import com.example.chat.models.MessageData
import com.example.chat.network.services.ChatWebSocketService
import com.example.chat.utils.Resource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import java.lang.Exception
import javax.inject.Inject

class ChatWebSocketServiceImpl @Inject constructor(private val client: HttpClient): ChatWebSocketService {

    private var webSocket:WebSocketSession?=null

    override suspend fun establishSession(userName: String): Resource<Unit> {
        return try {
            webSocket=client.webSocketSession {
                url { "${ChatWebSocketService.SocketUrl.SocketUrlApi.url}?username=$userName" }
            }
            if (webSocket?.isActive == true){
                Resource.Success(Unit)
            }else Resource.Error("Could not establish connection with server")

        }catch (e:Exception){
            Resource.Error(e.localizedMessage)
        }
    }

    override suspend fun sendMsg(message: String) {
        try {
            webSocket?.send(Frame.Text(message))
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun observeIncomingMsg(): Flow<MessageData> {
       return try {
            webSocket
                ?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json=(it as? Frame.Text)?.readText() ?:""
                    val messageData=Json.decodeFromString<MessageData>(json)
                    messageData
                }?: emptyFlow()

        }catch (e:Exception){
            e.printStackTrace()
            emptyFlow()
        }
    }

    override suspend fun closeSession() {
       webSocket?.close()
    }
}