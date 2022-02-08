package com.example.mylibrary.view.root.user

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.data.room.entity.Book
import com.example.mylibrary.databinding.ItemUserBinding
import com.example.mylibrary.view.root.home.dto.ItemClickArgs

class UserAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var content = mutableListOf<Book>()

    inner class UserViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(book: Book, position: Int){
            binding.book = book
            binding.setOnClickItem { view ->
                itemOnClickListener(ItemClickArgs(binding,view, position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UserViewHolder).bind(content[position],position)
    }

    override fun getItemCount(): Int = content.size
}