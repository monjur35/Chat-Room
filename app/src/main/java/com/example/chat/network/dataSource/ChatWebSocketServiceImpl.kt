package com.example.chat.network.dataSource

import android.util.Log
import com.example.chat.models.MessageData
import com.example.chat.network.services.ChatWebSocketService
import com.example.chat.utils.Resource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ChatWebSocketServiceImpl @Inject constructor(private val client: HttpClient) :
    ChatWebSocketService {

    private var webSocket: WebSocketSession? = null

    override suspend fun establishSession(userName: String): Resource<Unit> {

        var userName = "Monjur"

        return try {
            Log.e(
                "TAG",
                "establishSession: ${ChatWebSocketService.SocketUrl.SocketUrlApi.url}?username=$userName",
            )
            /* webSocket=client.webSocketSession {
                 url { "${ChatWebSocketService.SocketUrl.SocketUrlApi.url}}" }
             }*/

            client.webSocket(
                method = HttpMethod.Get,
                host = "100.100.100.79",
                port = 80,
                path = "/chat-socket?username=$userName"
            ) {
                // this: DefaultClientWebSocketSession
                webSocket = this
            }

            Log.e(
                "TAG",
                "establishSession: ${ChatWebSocketService.SocketUrl.SocketUrlApi.url}?username=$userName",
            )
            if (webSocket?.isActive == true) {
                Resource.Success(Unit)
            } else Resource.Error("Could not establish connection with server")

        } catch (e: Exception) {
            Log.e("TAG", "not establish Session: ${e.message}")
            Resource.Error(e.localizedMessage)

        }
    }

    override suspend fun sendMsg(message: String) {
        try {
            webSocket?.send(Frame.Text(message))
        } catch (e: Exception) {
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
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    val messageData = Json.decodeFromString<MessageData>(json)
                    messageData
                } ?: emptyFlow()

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAG", "observeIncomingMsg: " + e.localizedMessage)
            emptyFlow()
        }
    }

    override suspend fun closeSession() {
        webSocket?.close()
    }
}