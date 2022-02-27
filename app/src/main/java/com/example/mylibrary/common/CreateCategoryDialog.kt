package com.example.mylibrary.common

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mylibrary.data.room.entity.Category
import com.example.mylibrary.databinding.DlgCreateCategoryBinding
import com.example.mylibrary.view.edit.EditCategoryFragment
import com.example.mylibrary.view.edit.EditCategoryViewModel
import com.example.mylibrary.view.root.user.UserFragment
import com.example.mylibrary.view.root.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCategoryDialog: DialogFragment() {

    private var _binding: DlgCreateCategoryBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels({requireParentFragment()})
    private val editCategoryViewModel: EditCategoryViewModel by viewModels({ requireParentFragment() })

    private val searchEditActionListener: (TextView, Int, KeyEvent?) -> Boolean = { view, actionId, event ->
        when(actionId){
            EditorInfo.IME_ACTION_DONE -> setCategory(view)
            else -> false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DlgCreateCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    private fun setOnClickListener(){
        binding.editCreateCategoryCreation.setOnEditorActionListener(searchEditActionListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCategory(view: TextView): Boolean {
        view.text.toString().run {
            when {
                isNullOrBlank() -> showToast("폴더명을 입력해주세요.")
                this == "전체" -> showToast("'전체' 폴더명은 사용하실 수 없습니다.")
                else -> insertCategory()
            }
        }
        dismiss()
        return true
    }

    private fun insertCategory() {
        if (parentFragment is UserFragment) {
            userViewModel.insertCategory(Category(binding.editCreateCategoryCreation.text.toString()))
        } else if (parentFragment is EditCategoryFragment) {
            editCategoryViewModel.insertCategory(Category(binding.editCreateCategoryCreation.text.toString()))
        }
    }
}