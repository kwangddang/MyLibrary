package com.example.mylibrary

import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.room.entity.Book

abstract class BaseViewModel: ViewModel() {
    abstract fun insert(book: Book)
    abstract fun delete(isbn:String)
}