package com.example.mylibrary.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mylibrary.util.*
import com.example.mylibrary.databinding.FragmentSettingBinding
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment: Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingViewModel by viewModels()

    private val userObserver: (FirebaseUser?) -> Unit = { user ->
        if(user == null){
            Navigation.findNavController(binding.root).navigate(SettingFragmentDirections.actionSettingFragmentToLoginFragment())
        }
    }

    private val labelUsernameOnClickListener: (View) -> Unit = {
        if(KotPrefModel.loginMethod == LoginMethodConstant.NO_ACCOUNT)
            showToast(ToastConstant.NO_ACCOUNT_USERNAME)
        else
            Navigation.findNavController(binding.root).navigate(SettingFragmentDirections.actionSettingFragmentToSettingUsernameFragment())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater,container,false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        observeData()
    }

    private fun observeData(){
        viewModel.user.observe(viewLifecycleOwner, userObserver)
    }

    private fun setOnClickListener(){
        binding.textSettingLabelLogout.setOnClickListener { viewModel.logout() }
        binding.textSettingLabelUsername.setOnClickListener (labelUsernameOnClickListener)
        binding.imgSettingBack.setOnClickListener { Navigation.findNavController(binding.root).popBackStack() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}