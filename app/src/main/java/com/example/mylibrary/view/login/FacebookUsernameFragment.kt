package com.example.mylibrary.view.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mylibrary.common.KotPrefModel
import com.example.mylibrary.common.LoginMethodConstant
import com.example.mylibrary.common.showToast
import com.example.mylibrary.databinding.FragmentFacebookUsernameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FacebookUsernameFragment: Fragment() {
    private var _binding: FragmentFacebookUsernameBinding? = null
    val binding get() = _binding!!

    private val usernameObserver:(String) -> Unit = { username ->
        if(username.length < 2)
            binding.motionFacebookUsernameContainer.transitionToStart()
        else
            binding.motionFacebookUsernameContainer.transitionToEnd()
    }

    private val viewModel: LoginViewModel by viewModels({requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFacebookUsernameBinding.inflate(inflater,container,false)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setOnClickListeners()
    }

    private fun observeData(){
        viewModel.username.observe(viewLifecycleOwner,usernameObserver)
        viewModel.facebookUsernameSuccess.observe(viewLifecycleOwner,facebookUsernameObserver)
    }
    private val facebookUsernameObserver: (Boolean?) -> Unit = {
        when(it){
            true -> {
                KotPrefModel.loginMethod = LoginMethodConstant.FACEBOOK
                Navigation.findNavController(binding.root).navigate(FacebookUsernameFragmentDirections.actionFacebookUsernameFragmentToRootFragment())
                viewModel.initSuccessValue()
            }
            false -> {
                showToast("닉네임 설정에 실패하였습니다. 다시 시도해주세요.")
            }
        }
    }

    private fun setOnClickListeners(){
        binding.btnFacebookUsernameConfirm.setOnClickListener{viewModel.setUserInfo(binding.editFacebookUsernameInput.text.toString())}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}