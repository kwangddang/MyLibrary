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
import com.example.mylibrary.common.KotPrefModel
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

        override fun onError(error: FacebookException) {
        }
    }

    private val facebookSuccessObserver: (Boolean?) -> Unit = {
        when(it){
            true -> {
                KotPrefModel.loginMethod = "facebook"
                Navigation.findNavController(binding.root).navigate(LoginFragmentDirections.actionLoginFragmentToRootFragment())
            }
            false -> Navigation.findNavController(binding.root).navigate(LoginFragmentDirections.actionLoginFragmentToFacebookUsernameFragment())
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
        setTransparentStatusBar()
        autoLogin()
        observeData()
        setFacebookLogin()
        setOnClickListeners()
    }

    private fun autoLogin(){
        viewModel.autoLogin()
    }

    private fun observeData(){
        viewModel.facebookSuccess.observe(viewLifecycleOwner,facebookSuccessObserver)
        viewModel.autoLoginSuccess.observe(viewLifecycleOwner,autoLoginSuccessObserver)
    }

    private val autoLoginSuccessObserver:(Boolean) -> Unit = {
        if(it){
            KotPrefModel.loginMethod = "auto"
            Navigation.findNavController(binding.root).navigate(LoginFragmentDirections.actionLoginFragmentToRootFragment())
        }
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
        binding.constraintLoginEmailBtnContainer.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(LoginFragmentDirections.actionLoginFragmentToLoginEmailFragment())
        }
        binding.constraintLoginBtnContainer.setOnClickListener{
            KotPrefModel.loginMethod = "noAccount"
            Navigation.findNavController(binding.root).navigate(LoginFragmentDirections.actionLoginFragmentToRootFragment())
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