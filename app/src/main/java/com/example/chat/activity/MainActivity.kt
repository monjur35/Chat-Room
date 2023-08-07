package com.example.chat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.chat.R
import com.example.chat.databinding.ActivityMainBinding
import com.example.chat.models.MessageData
import com.example.chat.viewModels.ChatRoomViewModel
import com.example.chat.viewModels.UserNameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val userNameViewModel:UserNameViewModel by viewModels()
    private val chatViewModel:ChatRoomViewModel by viewModels()
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //userNameViewModel.setUserNameState("Monjur")
       /* binding.btiiiiiiiin.setOnClickListener{
            userNameViewModel.userNameTxt.observe(this, Observer {
                Log.e("TAG", "onCreate: $it" )
            })

            chatViewModel.chatMsgList.observe(this, Observer {
                Log.e("TAG", "onCreate: ${it.toString()}" )
            })
        }*/





    }
}