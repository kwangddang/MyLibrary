package com.example.mylibrary.view.root.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.data.room.entity.Book
import com.example.mylibrary.databinding.ItemUserBookBinding
import com.example.mylibrary.view.root.home.dto.ItemClickArgs

class UserBookAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit, private val itemOnLongClickListener: (ItemClickArgs?) -> Boolean): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var content = mutableListOf<Book>()

    inner class UserBookViewHolder(private val binding: ItemUserBookBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(book: Book, position: Int){
            binding.book = book
            binding.setOnClickItem { view ->
                itemOnClickListener(ItemClickArgs(binding,view, position))
            }
            binding.setOnLongClickItem { view ->
                itemOnLongClickListener(ItemClickArgs(binding,view,position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserBookViewHolder(ItemUserBookBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UserBookViewHolder).bind(content[position],position)
    }

    override fun getItemCount(): Int = content.size
}