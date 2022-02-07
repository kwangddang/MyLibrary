package com.example.mylibrary.view.root.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mylibrary.data.room.entity.Book
import com.example.mylibrary.databinding.FragmentUserBinding
import com.example.mylibrary.view.root.home.dto.ItemClickArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment: Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    private val adapter: UserAdapter by lazy{
        UserAdapter(itemOnClickListener)
    }

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->

    }

    private val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refresh()
        binding.swipeUserContainer.isRefreshing = false
    }

    private val bookObserver: (List<Book>) -> Unit = { book ->
        adapter.apply {
            content = book
            notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        refresh()
        initAdapter()
        setOnRefreshListener()
    }

    private fun setOnRefreshListener(){
        binding.swipeUserContainer.setOnRefreshListener(swipeRefreshListener)
    }

    private fun refresh(){
        viewModel.getMyBook()
    }

    private fun observeData(){
        viewModel.book.observe(viewLifecycleOwner,bookObserver)
    }

    private fun initAdapter() {
        binding.recyclerUser.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}