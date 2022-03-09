package com.example.mylibrary.view.root.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mylibrary.util.TagConstant
import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.databinding.FragmentHomeBinding
import com.example.mylibrary.databinding.ItemHomeBinding
import com.example.mylibrary.view.common.BookDetailDialog
import com.example.mylibrary.view.common.dto.ItemClickArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val bookAdapter: HomeAdapter by lazy {
        HomeAdapter(bookItemOnClickListener)
    }

    private val bookItemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        BookDetailDialog((args?.item as ItemHomeBinding).bookInfo!!, viewModel).show(childFragmentManager, TagConstant.BOOK_DETAIL_DIALOG)
    }

    private val bookObserver: (List<BookInfo>) -> Unit = { book ->
        bookAdapter.apply {
            content = book as MutableList<BookInfo>
            notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initAdapter()
        initViews()
    }

    private fun initViews() {
        viewModel.getPopularBook()
    }

    private fun initAdapter() {
        binding.recyclerHomePopular.adapter = bookAdapter
    }

    private fun observeData() {
        viewModel.book.observe(viewLifecycleOwner, bookObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}