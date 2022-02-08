package com.example.mylibrary.view.root.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mylibrary.data.room.entity.Category
import com.example.mylibrary.databinding.BottomsheetCategoryCreationBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryCreationBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: BottomsheetCategoryCreationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels({requireParentFragment()})

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
        binding.btnBottomsheetCategory.setOnClickListener {
            viewModel.insertCategory(Category(binding.editBottomsheetCategoryCreation.text.toString()))
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}