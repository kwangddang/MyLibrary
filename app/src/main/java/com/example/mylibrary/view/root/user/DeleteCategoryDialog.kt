package com.example.mylibrary.view.root.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mylibrary.common.KotPrefModel
import com.example.mylibrary.common.showToast
import com.example.mylibrary.databinding.DlgDeleteCategoryBinding

class DeleteCategoryDialog(private val category: String): DialogFragment() {
    private var _binding: DlgDeleteCategoryBinding? = null
    private val binding get() = _binding!!

    val viewModel: UserViewModel by viewModels({requireParentFragment()})

    private val textConfirmOnClickListener: (View) -> Unit = {
        if(category == "전체")
            showToast("'전체' 폴더는 삭제하실 수 없습니다.")
        else
            deleteCategory()
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

    private fun deleteCategory(){
        if(KotPrefModel.loginMethod == "noAccount")
            viewModel.deleteMyCategory(category)
        else
            viewModel.deleteUserCategory(category)
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