package com.example.mylibrary.data.repository

import com.example.mylibrary.data.entity.room.Book

interface BookRepository {
    suspend fun getMyBook(): List<Book>

    suspend fun checkMyBook(isbn: String): String

    suspend fun getMyCategoryBook(category: String): List<Book>

    suspend fun setMyBookCategory(category: String, isbn: String)

    suspend fun insert(book: Book)

    suspend fun delete(isbn: String)

    suspend fun deleteMyBookCategory(category: String)
}