package com.example.mylibrary.view.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.data.entity.firebase.Review
import com.example.mylibrary.databinding.ItemReviewBinding
import com.example.mylibrary.view.common.dto.ItemClickArgs

class ReviewAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var content = mutableListOf<Review>()

    inner class ReviewViewHolder(private val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(review: Review, position: Int){
            binding.review = review
            binding.setOnClickItem { view ->
                itemOnClickListener(ItemClickArgs(binding,view,position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReviewViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ReviewViewHolder).bind(content[position],position)
    }

    override fun getItemCount(): Int = content.size
}