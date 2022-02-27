package com.example.mylibrary.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mylibrary.common.setStatusBarOrigin
import com.example.mylibrary.common.setTransparentStatusBar
import com.example.mylibrary.databinding.FragmentLoginBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!

    private lateinit var callbackManager: CallbackManager

    private val viewModel: LoginViewModel by viewModels()

    private val facebookCallback = object: FacebookCallback<LoginResult>{
        override fun onSuccess(result: LoginResult) {
            val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
            viewModel.facebookLogin(credential)
        }

        override fun onCancel() {
        }

        override fun onError(error: FacebookException?) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbackManager = CallbackManager.Factory.create()
    }

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

        setFacebookLogin()
        setTransparentStatusBar()
        setOnClickListeners()
    }

    private fun setFacebookLogin(){
        binding.btnLoginFacebook.run{
            setPermissions("email","public_profile")
            fragment = this@LoginFragment
            registerCallback(callbackManager,facebookCallback)
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode,resultCode,data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().setStatusBarOrigin()
        _binding = null
    }
}