package com.example.mylibrary.view.root.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mylibrary.data.database.BookDatabase
import com.example.mylibrary.data.dto.request.BookRequest
import com.example.mylibrary.data.dto.response.BookResponse
import com.example.mylibrary.data.room.dao.BookDao
import com.example.mylibrary.data.room.entity.Book
import com.example.mylibrary.databinding.FragmentHomeBinding
import com.example.mylibrary.databinding.ItemHomeBinding
import com.example.mylibrary.view.root.home.dto.ItemClickArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val adapter: HomeAdapter by lazy {
        HomeAdapter(itemOnClickListener)
    }

    private val btnOnClickListener: (View) -> Unit = {
        viewModel.getBook(BookRequest(query = binding.editHomeSearch.text.toString()))
    }

    private val bookObserver: (BookResponse?) -> Unit = { response ->
        adapter.apply {
            item = response
            notifyDataSetChanged()
        }
    }

    @Inject
    lateinit var bookDao: BookDao

    @Suppress("DEPRECATION")
    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        val book = (args?.item as ItemHomeBinding).book
        Log.d("Test", "?")
        CoroutineScope(Dispatchers.IO).launch {
            bookDao.insert(
                Book(
                    author = book?.author,
                    description = book?.description,
                    discount = book?.discount,
                    image = book?.image,
                    isbn = book?.isbn,
                    link = book?.link,
                    price = book?.price,
                    pubdate = book?.pubdate,
                    publisher = book?.publisher,
                    title = book?.title
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initAdapter()
        setOnClickListener()
    }

    private fun observeData() {
        viewModel.book.observe(viewLifecycleOwner, bookObserver)
    }

    private fun setOnClickListener() {
        binding.btnHomeSearch.setOnClickListener(btnOnClickListener)
    }

    private fun initAdapter() {
        binding.recyclerHome.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}