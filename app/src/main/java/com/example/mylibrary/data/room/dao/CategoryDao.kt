package com.example.mylibrary.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mylibrary.data.room.entity.Book
import com.example.mylibrary.data.room.entity.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getCategory(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Query("DELETE FROM category WHERE category LIKE :category")
    fun delete(category: String)
}