package com.example.mylibrary.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mylibrary.data.room.entity.Category
import com.example.mylibrary.databinding.BottomsheetCategoryCreationBinding
import com.example.mylibrary.view.edit.EditCategoryFragment
import com.example.mylibrary.view.edit.EditCategoryViewModel
import com.example.mylibrary.view.root.user.UserFragment
import com.example.mylibrary.view.root.user.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryCreationBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: BottomsheetCategoryCreationBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels({requireParentFragment()})
    private val editCategoryViewModel: EditCategoryViewModel by viewModels({ requireParentFragment() })

    private val btnOnClickListener: (View) -> Unit = {
        if(parentFragment is UserFragment) {
            userViewModel.insertCategory(Category(binding.editBottomsheetCategoryCreation.text.toString()))
            (parentFragment as UserFragment).refresh()
        }
        else if(parentFragment is EditCategoryFragment) {
            editCategoryViewModel.insertCategory(Category(binding.editBottomsheetCategoryCreation.text.toString()))
            (parentFragment as EditCategoryFragment).refresh()
        }
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetCategoryCreationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener(){
        binding.btnBottomsheetCategory.setOnClickListener (btnOnClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}