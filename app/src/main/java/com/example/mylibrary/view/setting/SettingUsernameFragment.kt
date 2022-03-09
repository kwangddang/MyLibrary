package com.example.mylibrary.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mylibrary.util.showToast
import com.example.mylibrary.databinding.FragmentSettingUsernameBinding
import com.example.mylibrary.util.ToastConstant
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
                showToast(ToastConstant.SUCCESS_USERNAME_CHANGE)
                requireActivity().onBackPressed()
                initUsername()
            }
            false -> {
                showToast(ToastConstant.FAIL_USERNAME_CHANGE)
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
        binding.imgSettingUsernameBack.setOnClickListener { Navigation.findNavController(binding.root).popBackStack() }
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