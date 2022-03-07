package com.example.mylibrary.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mylibrary.common.KotPrefModel
import com.example.mylibrary.common.showToast
import com.example.mylibrary.databinding.FragmentLoginEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginEmailFragment: Fragment() {
    private var _binding: FragmentLoginEmailBinding? = null
    val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels({requireParentFragment()})

    private val emailSuccessObserver: (Boolean?) -> Unit = {
        when(it){
            true -> {
                KotPrefModel.loginMethod = "email"
                Navigation.findNavController(binding.root).navigate(LoginEmailFragmentDirections.actionLoginEmailFragmentToRootFragment())
                viewModel.initSuccessValue()
            }
            false -> showToast(requireContext(),"유효하지 않은 계정입니다. 다시 한번 입력해주세요.")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginEmailBinding.inflate(inflater,container,false)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setOnClickListeners()
    }

    private fun observeData(){
        viewModel.emailLoginSuccess.observe(viewLifecycleOwner,emailSuccessObserver)
    }

    private fun setOnClickListeners(){
        binding.btnLoginEmailConfirm.setOnClickListener { viewModel.emailLogin(binding.editLoginEmailEmail.text.toString(),binding.editLoginEmailPwd.text.toString()) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}