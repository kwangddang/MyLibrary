package com.example.mylibrary.view.login.signup

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mylibrary.util.signupFrom2Depth
import com.example.mylibrary.databinding.FragmentSignupEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupEmailFragment: Fragment() {
    private var _binding: FragmentSignupEmailBinding? = null
    val binding get() = _binding!!

    private val viewModel: SignupViewModel by viewModels({requireParentFragment()})

    private val emailObserver: (String) -> Unit = { email ->
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.motionSignupEmailContainer.transitionToEnd()
        } else {
            binding.motionSignupEmailContainer.transitionToStart()
        }
    }

    private val btnOnClickListener:(View) -> Unit = {
        Navigation.findNavController(binding.root).navigate(
            SignupEmailFragmentDirections.actionSignupEmailFragmentToSignupPasswordFragment()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupEmailBinding.inflate(inflater,container,false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        observeData()
    }

    private fun observeData(){
        viewModel.email.observe(viewLifecycleOwner, emailObserver)
    }

    private fun setOnClickListeners(){
        binding.btnSignupEmailConfirm.setOnClickListener (btnOnClickListener)
        signupFrom2Depth().binding.imgSignupBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}