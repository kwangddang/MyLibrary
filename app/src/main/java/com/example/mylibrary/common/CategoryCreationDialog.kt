package com.example.mylibrary.common

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
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
class CategoryCreationDialog : DialogFragment() {

    private var _binding: DlgCreateCategoryBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels({requireParentFragment()})
    private val editCategoryViewModel: EditCategoryViewModel by viewModels({ requireParentFragment() })

    private val searchEditActionListener: (TextView, Int, KeyEvent?) -> Boolean = { view, actionId, event ->
        when(actionId){
            EditorInfo.IME_ACTION_DONE -> {
                view.text.toString().run {
                    if(isNullOrBlank()) Toast.makeText(requireContext(),"폴더 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                    else {
                        if(parentFragment is UserFragment) {
                            userViewModel.insertCategory(Category(binding.editCreateCategoryCreation.text.toString()))
                            (parentFragment as UserFragment).refresh()
                        }
                        else if(parentFragment is EditCategoryFragment) {
                            editCategoryViewModel.insertCategory(Category(binding.editCreateCategoryCreation.text.toString()))
                            (parentFragment as EditCategoryFragment).refresh()
                        }
                    }
                }
                dismiss()
                true
            }
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
        setOnEditorActionListener()
    }

    private fun setOnClickListener(){
        binding.editCreateCategoryCreation.setOnEditorActionListener(searchEditActionListener)
    }

    private fun setOnEditorActionListener(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}