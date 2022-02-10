package com.example.mylibrary.common

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.mylibrary.data.dto.response.BookInfo
import com.example.mylibrary.data.room.entity.Book
fun bookInfoToBook(bookInfo: BookInfo): Book =
    Book(bookInfo.author,
        bookInfo.description,
        bookInfo.discount,
        bookInfo.image,
        bookInfo.isbn,
        bookInfo.link,
        bookInfo.price,
        bookInfo.pubdate,
        bookInfo.publisher,
        bookInfo.title,
        bookInfo.isBookMark,
    )

fun filteringText(text: String): String = text.replace("<b>","").replace("</b>","")

fun Activity.hideKeyboard(editText: EditText) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
}

fun Context.getPxFromDp(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

fun Fragment.showNoContentToast() {
    Toast.makeText(requireContext(), "텍스트를 입력해주세요.", Toast.LENGTH_SHORT).show()
}