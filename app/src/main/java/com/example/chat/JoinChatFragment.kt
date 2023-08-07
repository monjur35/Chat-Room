package com.example.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.chat.databinding.FragmentJoinChatBinding
import com.example.chat.viewModels.ChatRoomViewModel
import com.example.chat.viewModels.UserNameViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinChatFragment : Fragment() {
    private var _binding: FragmentJoinChatBinding? = null
    private val binding get() = _binding
    private val userNameViewModel: UserNameViewModel by viewModels()
    private val chatRoomViewModel: ChatRoomViewModel by viewModels<ChatRoomViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJoinChatBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { bi ->
            bi.joinBtn.setOnClickListener {
                userNameViewModel.userNameTxt.value = bi.usernameET.text.toString()
                userNameViewModel.isLoading.value = true
                userNameViewModel.onJoinChatClick()

            }
        }


        userNameViewModel.userNameTxt.observe(viewLifecycleOwner) {
            chatRoomViewModel.establishSession(it)
        }


        userNameViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            Log.e("TAG", "onCreate: $it")
            if (it) binding?.spinKit?.visibility = View.VISIBLE else binding?.spinKit?.visibility =
                View.GONE
        })
    }
}