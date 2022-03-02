package com.example.mylibrary

import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.entity.firebase.Book.UserBook
import com.example.mylibrary.data.entity.room.Book

abstract class BaseViewModel: ViewModel() {
    abstract fun insertMyBook(book: Book)
    abstract fun deleteMyBook(isbn:String)
    abstract fun insertUserBook(book: UserBook)
    abstract fun deleteUserBook(isbn:String)
}