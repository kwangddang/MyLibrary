package com.example.mylibrary.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.mylibrary.common.setStatusBarOrigin
import com.example.mylibrary.common.setTransparentStatusBar
import com.example.mylibrary.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTransparentStatusBar()
        setOnClickListeners()
    }

    private fun setTransparentStatusBar(){
        requireActivity().setTransparentStatusBar()
        binding.constraintLoginInnerContainer.setTransparentStatusBar(requireActivity())
    }

    private fun setOnClickListeners(){
        binding.constraintLoginBtnContainer.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(LoginFragmentDirections.actionLoginFragmentToLoginEmailFragment())
        }
        binding.textLoginSignup.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().setStatusBarOrigin()
        _binding = null
    }
}