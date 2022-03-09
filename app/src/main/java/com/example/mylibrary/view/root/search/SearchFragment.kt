package com.example.mylibrary.view.root.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mylibrary.util.TagConstant
import com.example.mylibrary.util.hideKeyboard
import com.example.mylibrary.util.showToast
import com.example.mylibrary.databinding.FragmentSearchBinding
import com.example.mylibrary.databinding.ItemSearchBinding
import com.example.mylibrary.util.ToastConstant
import com.example.mylibrary.view.common.BookDetailDialog
import com.example.mylibrary.view.common.dto.ItemClickArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()
    private val adapter: SearchAdapter by lazy {
        SearchAdapter(itemOnClickListener)
    }

    private val searchEditActionListener: (TextView, Int, KeyEvent?) -> Boolean = { view, actionId, event ->
        when(actionId){
            EditorInfo.IME_ACTION_SEARCH -> {
                binding.recyclerSearch.smoothScrollToPosition(0)
                setSearchResult(view)
            }
            else -> false
        }
    }

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        BookDetailDialog((args?.item as ItemSearchBinding).book!!, viewModel).show(childFragmentManager,TagConstant.BOOK_DETAIL_DIALOG)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initAdapter()
        setOnEditorActionListener()
    }

    private fun observeData() {

    }

    private fun setOnEditorActionListener() {
        binding.editSearch.setOnEditorActionListener(searchEditActionListener)
    }

    private fun initAdapter() {
        binding.recyclerSearch.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setSearchResult(view: TextView): Boolean {
        view.text.toString().run {
            if (isNullOrBlank()) showToast(ToastConstant.NO_SEARCH_TEXT)
            else {
                lifecycleScope.launch {
                    viewModel.getBook(view.text.toString()).collect {
                        adapter.submitData(it)
                    }
                }
            }
        }
        requireActivity().hideKeyboard(view as EditText)
        return true
    }

}