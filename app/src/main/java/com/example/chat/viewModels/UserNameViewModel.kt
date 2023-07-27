package com.example.chat.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class UserNameViewModel():ViewModel() {
    val userNameTxt= MutableLiveData<String>("")
    private val _onJoinRoom= MutableSharedFlow<String>()
    val onJoinRoom=_onJoinRoom.asSharedFlow()

    fun setUserNameState(userName: String) {
        userNameTxt.value = userName
    }


    fun onJoinChatClick(){
        viewModelScope.launch {
            if (userNameTxt.value?.isNotBlank() == true){
                _onJoinRoom.emit(userNameTxt.value!!)
            }
        }
    }
}