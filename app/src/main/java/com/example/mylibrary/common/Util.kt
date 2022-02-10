package com.example.mylibrary.common

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.mylibrary.data.dto.response.BookInfo
import com.example.mylibrary.data.room.entity.Book


fun bookToBookInfo(book: Book): BookInfo =
    BookInfo(
        book.author,
        book.description,
        book.discount,
        book.image,
        book.isbn,
        book.link,
        book.price,
        book.pubdate,
        book.publisher,
        book.title,
        book.isBookMark
    )

fun bookInfoToBook(bookInfo: BookInfo): Book =
    Book(
        bookInfo.author,
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

