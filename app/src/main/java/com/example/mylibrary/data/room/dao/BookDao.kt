package com.example.mylibrary.data.room.dao

import androidx.room.*
import com.example.mylibrary.data.room.entity.Book

@Dao
interface BookDao {
    @Query("SELECT * FROM book")
    fun getMyBook(): List<Book>

//    @Query("SELECT isbn FROM book ")    //TODO 데이터베이스 검색 구현

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(book: Book)

    @Delete
    fun delete(book: Book)
}