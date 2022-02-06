package com.example.mylibrary.data.repository

import com.example.mylibrary.data.room.dao.BookDao
import com.example.mylibrary.data.room.entity.Book
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val bookDao: BookDao): BookRepository {
    override fun getMyBook(): List<Book> = bookDao.getMyBook()

    override fun insert(book: Book) = bookDao.insert(book)

    override fun delete(book: Book) = bookDao.delete(book)
}