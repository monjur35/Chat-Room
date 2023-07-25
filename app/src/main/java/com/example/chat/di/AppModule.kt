package com.example.chat.di

import com.example.chat.network.dataSource.ChatWebSocketServiceImpl
import com.example.chat.network.dataSource.MessageServiceImpl
import com.example.chat.network.services.ChatWebSocketService
import com.example.chat.network.services.MessageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKtorClient():HttpClient{
        return HttpClient(CIO){
            install(Logging)
            install(WebSockets)
            //missing json
        }
    }


    @Provides
    @Singleton
    fun providesChatWebSocketService(client: HttpClient):ChatWebSocketService{
        return ChatWebSocketServiceImpl(client)
    }

     @Provides
    @Singleton
    fun providesChatService(client: HttpClient):MessageService{
        return MessageServiceImpl(client)
    }


}