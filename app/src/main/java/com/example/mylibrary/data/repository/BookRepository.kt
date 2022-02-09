package com.example.mylibrary.data.repository

import com.example.mylibrary.data.room.entity.Book

interface BookRepository {
    suspend fun getMyBook(): List<Book>

    suspend fun checkMyBook(isbn: String): String

    suspend fun getCategoryBook(category: String): List<Book>

    suspend fun setBookCategory(category: String, isbn: String)

    suspend fun insert(book: Book)

    suspend fun delete(isbn: String)
}