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
import com.example.mylibrary.R
import com.example.mylibrary.common.BookDetailDialog
import com.example.mylibrary.common.TagConstant
import com.example.mylibrary.common.hideKeyboard
import com.example.mylibrary.common.showToast
import com.example.mylibrary.data.dto.BookResponse
import com.example.mylibrary.databinding.FragmentSearchBinding
import com.example.mylibrary.databinding.ItemSearchBinding
import com.example.mylibrary.view.root.search.dto.ItemClickArgs
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

    private val bookObserver: (BookResponse?) -> Unit = { response ->
        showProperViews(response)
    }

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        when(args?.view?.id){
            R.id.constraint_isearch_innercontainer -> {
                BookDetailDialog((args.item as ItemSearchBinding).book!!, viewModel).show(childFragmentManager,TagConstant.BOOK_DETAIL_DIALOG)
            }
            //R.id.card_ihome_innercontainer -> startActivity(Intent(Intent.ACTION_VIEW,Uri.parse((args.item as ItemHomeBinding).book?.link)))
            //R.id.lottie_ihome_bookmark -> setBookMark(args.view as LottieAnimationView, args.item as ItemHomeBinding)
        }
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
        viewModel.book.observe(viewLifecycleOwner, bookObserver)
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
            if (isNullOrBlank()) showToast("검색어를 입력해주세요.")
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

    private fun showProperViews(response: BookResponse?) {
        if (response?.bookInfos!!.isEmpty()) {
            binding.imgSearchNosearch.visibility = View.VISIBLE
            binding.textSearchNosearchHead.visibility = View.VISIBLE
            binding.textSearchNosearchSubhead.visibility = View.VISIBLE
            binding.recyclerSearch.visibility = View.INVISIBLE
        } else {
            binding.imgSearchNosearch.visibility = View.INVISIBLE
            binding.textSearchNosearchHead.visibility = View.INVISIBLE
            binding.textSearchNosearchSubhead.visibility = View.INVISIBLE
            binding.recyclerSearch.visibility = View.VISIBLE
        }
    }
}