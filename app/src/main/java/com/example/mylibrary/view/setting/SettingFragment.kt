package com.example.mylibrary.view.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mylibrary.common.rootFrom1Depth
import com.example.mylibrary.common.rootFrom2Depth
import com.example.mylibrary.databinding.FragmentSettingBinding
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater,container,false)

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
        binding.textSettingLabelUsername.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(SettingFragmentDirections.actionSettingFragmentToSettingUsernameFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}