package com.example.mylibrary.data.dao

import androidx.room.*
import com.example.mylibrary.data.entity.room.Book

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
    fun getMyCategoryBook(category: String): List<Book>

    @Query("UPDATE book SET category = :category WHERE isbn LIKE :isbn")
    fun setMyBookCategory(category: String, isbn: String)

    @Query("UPDATE book SET category = null WHERE category LIKE :category")
    fun deleteMyBookCategory(category: String)
}