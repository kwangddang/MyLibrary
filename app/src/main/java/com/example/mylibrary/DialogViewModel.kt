package com.example.mylibrary

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mylibrary.data.dto.response.BookInfo
import com.example.mylibrary.data.entity.room.Book
import com.example.mylibrary.data.entity.room.Category

open class DialogViewModel: ViewModel() {
    open val bookmarkStatus: LiveData<Boolean?> = liveData {  }
    open fun setMyBook(book: Book){}
    open fun deleteMyBook(isbn:String){}
    open fun setUserBook(book: BookInfo){}
    open fun deleteUserBook(isbn:String){}
    open fun getBookmarked(isbn: String){}
    open fun setMyCategory(category: Category){}
    open fun setUserCategory(category: String){}
}