package com.example.mylibrary.view.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.data.room.entity.Category
import com.example.mylibrary.databinding.ItemUserCategoryBinding
import com.example.mylibrary.view.root.search.dto.ItemClickArgs

class EditCategoryAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var content = mutableListOf<Category>()

    inner class EditCategoryViewHolder(private val binding: ItemUserCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(category: Category, position: Int){
            binding.category = category
            binding.setOnClickItem { view ->
                itemOnClickListener(ItemClickArgs(binding,view,position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EditCategoryViewHolder(
            ItemUserCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EditCategoryAdapter.EditCategoryViewHolder).bind(content[position],position)
    }

    override fun getItemCount(): Int = content.size
}