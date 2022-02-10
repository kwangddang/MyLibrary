package com.example.mylibrary.view.root.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mylibrary.databinding.DlgDeleteCategoryBinding

class DeleteCategoryDialog(private val category: String): DialogFragment() {
    private var _binding: DlgDeleteCategoryBinding? = null
    private val binding get() = _binding!!

    val viewModel: UserViewModel by viewModels({requireParentFragment()})

    private val textConfirmOnClickListener: (View) -> Unit = {
        viewModel.deleteCategory(category)
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DlgDeleteCategoryBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        binding.textDlgDeleteCancel.setOnClickListener { dismiss() }
        binding.textDlgDeleteConfirm.setOnClickListener (textConfirmOnClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}