package com.example.mylibrary.view.root.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.data.dto.response.BookInfo
import com.example.mylibrary.data.dto.response.BookResponse
import com.example.mylibrary.databinding.ItemHomeBinding

class HomeAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var content = emptyList<BookInfo>()
    var item: BookResponse? = null
        set(value) {
            content = value?.bookInfos!!
            field = value
        }

    inner class HomeViewHolder(private val binding: ItemHomeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(bookInfo: BookInfo){
            binding.book = bookInfo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeViewHolder).bind(content[position])
    }

    override fun getItemCount(): Int = content.size
}