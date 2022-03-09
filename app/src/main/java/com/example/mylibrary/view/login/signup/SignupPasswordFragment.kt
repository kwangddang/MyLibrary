package com.example.mylibrary.view.login.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mylibrary.R
import com.example.mylibrary.util.signupFrom2Depth
import com.example.mylibrary.databinding.FragmentSignupPwdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupPasswordFragment: Fragment() {
    private var _binding: FragmentSignupPwdBinding? = null
    val binding get() = _binding!!

    private val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    private val btnOnClickListener:(View) -> Unit = {
        Navigation.findNavController(binding.root).navigate(
            SignupPasswordFragmentDirections.actionSignupPasswordFragmentToSignupUsernameFragment()
        )
    }

    private val passwordObserver: (String) -> Unit = { password ->
        if(password.length < 6){
            binding.motionSignupPwdContainer.transitionToState(R.id.password_input_signup_verification_start)
        } else{
            binding.motionSignupPwdContainer.transitionToState(R.id.password_input_signup_verification_end)
        }
    }

    private val passwordVerificationObserver: (String) -> Unit = { password ->
        if(password == viewModel.password.value && password.length >= 6){
            binding.motionSignupPwdContainer.transitionToState(R.id.password_input_signup_button_end)
        } else if(password.isNotBlank()){
            binding.motionSignupPwdContainer.transitionToState(R.id.password_input_signup_button_start)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupPwdBinding.inflate(inflater,container,false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        observeData()
    }

    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                binding.editSignupPwdInput.setText("")
                binding.editSignupPwdConfirmInput.setText("")
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    private fun setOnClickListeners(){
        binding.btnSignupPwdConfirm.setOnClickListener(btnOnClickListener)
        signupFrom2Depth().binding.imgSignupBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun observeData(){
        viewModel.password.observe(viewLifecycleOwner,passwordObserver)
        viewModel.passwordVerification.observe(viewLifecycleOwner,passwordVerificationObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}