package com.example.mylibrary.view.home

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


object HomeBindingAdapter {

    @JvmStatic
    @BindingAdapter("bookImage")
    fun setBookImage(view: ImageView, url: String?){
        view.context.apply {
            Glide.with(this)
                .load(url)
                .centerCrop()
                .into(view)
        }
    }
}