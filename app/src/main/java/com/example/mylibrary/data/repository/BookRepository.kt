package com.example.mylibrary.data.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mylibrary.data.room.entity.Book

interface BookRepository {
    fun getMyBook(): List<Book>

    fun checkMyBook(isbn: String): String

    fun insert(book: Book)

    fun delete(book: Book)
}