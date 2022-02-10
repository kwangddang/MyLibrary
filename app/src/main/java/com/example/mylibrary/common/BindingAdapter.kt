package com.example.mylibrary.common

import android.os.Build
import android.text.InputFilter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide


object BindingAdapter {

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
    @BindingAdapter("searchText")
    fun setSearchText(view: TextView, text: String?){
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

    @JvmStatic
    @BindingAdapter("searchEditText")
    fun setSearchEditText(view: EditText, any: Any?) {
        val context = view.context
        context.apply {
            view.minHeight = getPxFromDp(0.0f)
            view.setPadding(0, getPxFromDp(12.0f), 0, getPxFromDp(12.0f))
            view.maxLines = 1
            view.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(15))
            view.setBackgroundColor(context.getColor(android.R.color.transparent))
        }
    }

}