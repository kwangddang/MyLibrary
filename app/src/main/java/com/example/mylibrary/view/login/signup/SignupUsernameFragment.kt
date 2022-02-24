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
import com.example.mylibrary.common.signupFrom2Depth
import com.example.mylibrary.databinding.FragmentSignupUsernameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupUsernameFragment: Fragment() {
    private var _binding: FragmentSignupUsernameBinding? = null
    val binding get() = _binding!!

    private val viewModel: SignupViewModel by viewModels({requireParentFragment()})

    private val usernameObserver:(String) -> Unit = { username ->
        if(username.length < 2)
            binding.motionSignupUsernameContainer.transitionToStart()
        else
            binding.motionSignupUsernameContainer.transitionToEnd()
    }

    private val btnOnClickListener:(View) -> Unit = {
        Navigation.findNavController(signupFrom2Depth().binding.root).navigate(
            SignupFragmentDirections.actionSignupFragmentToRootFragment()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupUsernameBinding.inflate(inflater,container,false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        observeData()
    }

    private fun setOnClickListeners(){
        binding.btnSignupUsernameConfirm.setOnClickListener (btnOnClickListener)
        signupFrom2Depth().binding.imgSignupBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun observeData(){
        viewModel.username.observe(viewLifecycleOwner,usernameObserver)
    }

    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.editSignupUsernameInput.setText("")
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}