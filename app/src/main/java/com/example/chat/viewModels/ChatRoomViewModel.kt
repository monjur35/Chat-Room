package com.example.chat.viewModels


import android.os.Message
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.models.MessageData
import com.example.chat.repo.Repository
import com.example.chat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _messageTxt=MutableLiveData<String>("")
   // val messageTxt: State<String> =_messageTxt

    public val chatMsgList=MutableLiveData<List<MessageData>>()

    private val _toastEvent= MutableSharedFlow<String>()
    val toastEvent=_toastEvent.asSharedFlow()

    init {
        getAllMsg()
        savedStateHandle.get<String>("username")?:let {username->
            viewModelScope.launch {
                val result=repository.establishSession(username.toString())
                when(result){
                    is Resource.Success->{
                        repository.observeIncomingMsg().onEach {
                            val newMsgList= chatMsgList.value?.toMutableList()?.apply {
                                add(0,it)
                            }
                            chatMsgList.value=newMsgList
                        }.launchIn(viewModelScope)
                    }
                    is Resource.Error->{
                        result.message?.let { _toastEvent.emit(it) } ?:"Unknown error"
                    }

                    else -> {}
                }
            }
        }
    }


    fun getAllMsg(): MutableLiveData<List<MessageData>> {
        val mutableLiveData=MutableLiveData<List<MessageData>>()
        viewModelScope.launch {
            val response =repository.getAllMsg()
            mutableLiveData.postValue(response)
        }
        return mutableLiveData
    }


    fun onMsgChange(message: String){
        _messageTxt.postValue(message)
    }

   private fun stopSession(){
        viewModelScope.launch {
            repository.closeConnection()
        }
    }
    fun sendMsg(){
        viewModelScope.launch {
            repository.sendMsg(_messageTxt.value.toString())
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopSession()
    }
}