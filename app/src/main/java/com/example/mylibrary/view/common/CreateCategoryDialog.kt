package com.example.mylibrary.view.common

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.mylibrary.util.KotPrefModel
import com.example.mylibrary.util.LoginMethodConstant
import com.example.mylibrary.util.ToastConstant
import com.example.mylibrary.util.showToast
import com.example.mylibrary.data.entity.room.Category
import com.example.mylibrary.databinding.DlgCreateCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCategoryDialog(private val viewModel: DialogViewModel): DialogFragment() {

    private var _binding: DlgCreateCategoryBinding? = null
    private val binding get() = _binding!!

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
                isNullOrBlank() -> showToast(ToastConstant.NO_NAME)
                this == ToastConstant.WHOLE -> showToast(ToastConstant.NAME_ERROR)
                else -> insertCategory()
            }
        }
        dismiss()
        return true
    }

    private fun insertCategory() {
        if (KotPrefModel.loginMethod == LoginMethodConstant.NO_ACCOUNT) {
            viewModel.setMyCategory(Category(binding.editCreateCategoryCreation.text.toString()))
        } else {
            viewModel.setUserCategory(binding.editCreateCategoryCreation.text.toString())
        }
    }
}