package com.example.mylibrary.view.root.home

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.mylibrary.data.repository.BookRepository
import javax.inject.Inject


object HomeBindingAdapter {

    @JvmStatic
    @BindingAdapter("imageBook")
    fun setImageBook(view: ImageView, url: String?){
        view.context.apply {
            Glide.with(this)
                .load(url)
                .centerCrop()
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("textPrice")
    fun setTextPrice(view: TextView, text: String){
        view.text = "${text}원"
    }

    @JvmStatic
    @BindingAdapter("textTitle")
    fun setTextTitle(view: TextView, text: String?){
        view.text = text?.replace("<b>","")?.replace("</b>","")
    }

    @JvmStatic
    @BindingAdapter("textDescription")
    fun setTextDescription(view: TextView, text: String?){
        view.text = text?.replace("<b>","")?.replace("</b>","") + "\n"
    }

    @JvmStatic
    @BindingAdapter("checkBookMark")
    fun checkBookMark(view: LottieAnimationView, isBookMark: Boolean?){
        if(isBookMark!!) view.progress = 0.5f
        else view.progress = 0f
    }

}