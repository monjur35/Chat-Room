package com.example.chat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.chat.R
import com.example.chat.models.MessageData
import com.example.chat.viewModels.ChatRoomViewModel
import com.example.chat.viewModels.UserNameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val userNameViewModel:UserNameViewModel by viewModels()
    private val chatViewModel:ChatRoomViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn=findViewById<Button>(R.id.hgh)

        userNameViewModel.setUserNameState("MOnjur")
        btn.setOnClickListener{
            userNameViewModel.userNameTxt.observe(this, Observer {
                Log.e("TAG", "onCreate: $it" )
            })

            chatViewModel.chatMsgList.observe(this, Observer {
                Log.e("TAG", "onCreate: ${it.toString()}" )
            })
        }

    }
}