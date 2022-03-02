package com.example.mylibrary.view.root.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.data.entity.room.Book
import com.example.mylibrary.databinding.ItemUserBookBinding
import com.example.mylibrary.view.root.search.dto.ItemClickArgs

class HomeAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var content = mutableListOf<Book>()

    inner class HomeViewHolder(private val binding: ItemUserBookBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(book: Book, position: Int){
            binding.book = book
            binding.setOnClickItem { view ->
                itemOnClickListener(ItemClickArgs(binding,view, position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder(ItemUserBookBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeViewHolder).bind(content[position],position)
    }

    override fun getItemCount(): Int = content.size

}