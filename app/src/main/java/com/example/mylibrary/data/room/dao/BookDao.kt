package com.example.mylibrary.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mylibrary.data.room.entity.Book

@Dao
interface BookDao {
    @Query("SELECT * FROM book")
    fun getAll(): List<Book>

    @Insert
    fun insertAll(vararg books: Book)

    @Delete
    fun delete(book: Book)
}