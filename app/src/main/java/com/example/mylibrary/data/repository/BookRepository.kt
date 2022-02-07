package com.example.mylibrary.data.repository

import com.example.mylibrary.data.room.entity.Book

interface BookRepository {
    fun getMyBook(): List<Book>

    fun checkMyBook(isbn: String): String

    suspend fun insert(book: Book)

    suspend fun delete(isbn: String)
}