package com.example.mylibrary.view.root.home

import android.widget.ImageView
import android.widget.TextView
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

    @JvmStatic
    @BindingAdapter("titleFilter")
    fun setTitleFilter(view: TextView, text: String?){
        view.text = text?.replace("<b>","")?.replace("</b>","")
    }

    @JvmStatic
    @BindingAdapter("descriptionFilter")
    fun setDescriptionFilter(view: TextView, text: String?){
        view.text = text?.replace("<b>","")?.replace("</b>","")
    }
}