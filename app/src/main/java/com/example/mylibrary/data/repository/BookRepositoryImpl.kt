package com.example.mylibrary.data.repository

import com.example.mylibrary.data.dao.BookDao
import com.example.mylibrary.data.entity.room.Book
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val bookDao: BookDao): BookRepository {
    override suspend fun getMyBook(): List<Book> = bookDao.getMyBook()

    override suspend fun checkMyBook(isbn: String): String = bookDao.checkMyBook(isbn)

    override suspend fun getMyCategoryBook(category: String): List<Book> = bookDao.getMyCategoryBook(category)

    override suspend fun setMyBookCategory(category: String, isbn: String) = bookDao.setMyBookCategory(category,isbn)

    override suspend fun insert(book: Book) = bookDao.insert(book)

    override suspend fun delete(isbn: String) = bookDao.delete(isbn)

    override suspend fun deleteMyBookCategory(category: String) = bookDao.deleteMyBookCategory(category)
}