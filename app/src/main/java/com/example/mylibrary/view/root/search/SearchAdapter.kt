package com.example.mylibrary.view.root.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.databinding.ItemSearchBinding
import com.example.mylibrary.view.common.dto.ItemClickArgs

class SearchAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): PagingDataAdapter<BookInfo, SearchAdapter.SearchViewHolder>(
    COMPARATOR
){

    inner class SearchViewHolder(private val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(bookInfo: BookInfo, position: Int){
            binding.book = bookInfo
            binding.setOnClickItem { view ->
                itemOnClickListener(ItemClickArgs(binding,view,position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
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