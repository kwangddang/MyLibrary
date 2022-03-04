package com.example.mylibrary.view.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mylibrary.common.showToast
import com.example.mylibrary.databinding.FragmentSettingUsernameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingUsernameFragment: Fragment() {
    private var _binding: FragmentSettingUsernameBinding? = null
    val binding get() = _binding!!

    private val viewModel: SettingUsernameViewModel by viewModels()

    private val usernameObserver: (String) -> Unit = { username ->
        if(username.length < 2)
            binding.motionSettingUsernameInnercontainer.transitionToStart()
        else
            binding.motionSettingUsernameInnercontainer.transitionToEnd()
    }

    private val usernameSuccessObserver: (Boolean?) -> Unit = {
        when(it){
            true -> {
                showToast("닉네임 변경에 성공하였습니다.")
                requireActivity().onBackPressed()
                initUsername()
            }
            false -> {
                showToast("닉네임 변경에 실패하였습니다.")
                initUsername()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingUsernameBinding.inflate(inflater,container,false)
        binding.vm = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUsername()
        observeData()
        setOnClickListeners()
    }

    private fun initUsername() {
        viewModel.initUsername()
    }

    private fun setOnClickListeners(){
        binding.btnSettingUsernameConfirm.setOnClickListener { viewModel.setUsername() }
    }

    private fun observeData(){
        viewModel.username.observe(viewLifecycleOwner,usernameObserver)
        viewModel.usernameSuccess.observe(viewLifecycleOwner,usernameSuccessObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}