package com.example.mylibrary.data.room.dao

import androidx.room.*
import com.example.mylibrary.data.room.entity.Book

@Dao
interface BookDao {
    @Query("SELECT * FROM book")
    fun getMyBook(): List<Book>

    @Query("SELECT isbn FROM book WHERE isbn LIKE :isbn")
    fun checkMyBook(isbn: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(book: Book)

    @Query("DELETE FROM book WHERE isbn LIKE :isbn")
    fun delete(isbn: String)

    @Query("SELECT * FROM book WHERE category LIKE :category")
    fun getCategoryBook(category: String): List<Book>
}