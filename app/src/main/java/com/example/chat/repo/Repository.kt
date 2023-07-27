package com.example.chat.repo

import com.example.chat.network.services.ChatWebSocketService
import com.example.chat.network.services.MessageService
import javax.inject.Inject

class Repository @Inject constructor(
    private val messageService: MessageService,
    private val webSocketService: ChatWebSocketService
) {

    suspend fun getAllMsg()=messageService.getAllMsg()

    // region socket connection
    suspend fun establishSession(username:String)=webSocketService.establishSession(username)

    suspend fun sendMsg(message:String)=webSocketService.sendMsg(message)

    fun observeIncomingMsg()=webSocketService.observeIncomingMsg()

    suspend fun closeConnection()=webSocketService.closeSession()

}