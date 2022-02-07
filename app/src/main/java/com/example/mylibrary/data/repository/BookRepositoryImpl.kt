package com.example.mylibrary.data.repository

import com.example.mylibrary.data.room.dao.BookDao
import com.example.mylibrary.data.room.entity.Book
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val bookDao: BookDao): BookRepository {
    override fun getMyBook(): List<Book> = bookDao.getMyBook()

    override fun checkMyBook(isbn: String): String = bookDao.checkMyBook(isbn)

    override suspend fun insert(book: Book) = bookDao.insert(book)

    override suspend fun delete(isbn: String) = bookDao.delete(isbn)
}