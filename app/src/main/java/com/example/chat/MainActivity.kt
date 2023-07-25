package com.example.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chat.models.MessageData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val a= MessageData("","sad",1627214400000L,"monjur")
        Log.e("TAG", "onCreate: ${a.formattedDate}" )
    }
}