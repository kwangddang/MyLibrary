package com.example.mylibrary.view.root.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.databinding.ItemHomeBinding
import com.example.mylibrary.view.common.dto.ItemClickArgs

class HomeAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var content = mutableListOf<BookInfo>()

    inner class HomeViewHolder(private val binding: ItemHomeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(book: BookInfo, position: Int){
            binding.bookInfo = book
            binding.setOnClickItem { view ->
                itemOnClickListener(ItemClickArgs(binding,view, position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeViewHolder).bind(content[position],position)
    }

    override fun getItemCount(): Int = content.size

}