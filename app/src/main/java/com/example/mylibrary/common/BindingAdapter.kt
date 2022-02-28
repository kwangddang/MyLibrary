package com.example.mylibrary.common

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.mylibrary.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("imageBook")
    fun setImageBook(view: ShapeableImageView, url: String?){
        view.context.apply {
            val radius = resources.getDimension(R.dimen.album_corner_radius)
            val shapeAppearanceModel = view.shapeAppearanceModel.toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,radius)
                .build()
            view.shapeAppearanceModel = shapeAppearanceModel
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

    @JvmStatic
    @BindingAdapter("usernameValidation")
    fun setUsernameValidation(view: EditText, text: String){

    }

    @JvmStatic
    @BindingAdapter("usernameValidationAttrChanged")
    fun setUsernameValidationInverseBindingListener(view: EditText, listener: InverseBindingListener){
        view.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                listener.onChange()
            }

        })
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "usernameValidation", event = "usernameValidationAttrChanged")
    fun getUsernameValidation(view: EditText): String{
        return view.text.toString()
    }

}