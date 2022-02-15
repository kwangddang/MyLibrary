package com.example.mylibrary.view.root.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.data.dto.response.BookInfo
import com.example.mylibrary.data.dto.response.BookResponse
import com.example.mylibrary.databinding.ItemHomeBinding
import com.example.mylibrary.view.root.home.dto.ItemClickArgs

class HomeAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): PagingDataAdapter<BookInfo, HomeAdapter.HomeViewHolder>(
    COMPARATOR
){

    inner class HomeViewHolder(private val binding: ItemHomeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(bookInfo: BookInfo, position: Int){
            binding.book = bookInfo
            binding.setOnClickItem { view ->
                itemOnClickListener(ItemClickArgs(binding,view,position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it,position)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<BookInfo>() {
            override fun areItemsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean {
                return oldItem.isbn == newItem.isbn
            }

            override fun areContentsTheSame(oldItem: BookInfo, newItem: BookInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}