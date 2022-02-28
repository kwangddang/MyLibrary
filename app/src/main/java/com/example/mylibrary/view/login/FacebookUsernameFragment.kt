package com.example.mylibrary.view.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mylibrary.databinding.FragmentFacebookUsernameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FacebookUsernameFragment: Fragment() {
    private var _binding: FragmentFacebookUsernameBinding? = null
    val binding get() = _binding!!

    private val btnOnClickListener: (View) -> Unit = {
        viewModel.setUserInfo(binding.editFacebookUsernameInput.text.toString())
        Navigation.findNavController(binding.root).navigate(FacebookUsernameFragmentDirections.actionFacebookUsernameFragmentToRootFragment())
    }

    private val usernameObserver:(String) -> Unit = { username ->
        if(username.length < 2)
            binding.motionFacebookUsernameContainer.transitionToStart()
        else
            binding.motionFacebookUsernameContainer.transitionToEnd()
    }

    private val viewModel: LoginViewModel by viewModels({requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFacebookUsernameBinding.inflate(inflater,container,false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setOnClickListeners()
    }

    private fun observeData(){
        viewModel.username.observe(viewLifecycleOwner,usernameObserver)
    }

    private fun setOnClickListeners(){
        binding.btnFacebookUsernameConfirm.setOnClickListener(btnOnClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}