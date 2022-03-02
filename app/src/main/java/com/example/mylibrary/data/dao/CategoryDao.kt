package com.example.mylibrary.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mylibrary.data.entity.room.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getMyCategory(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Query("DELETE FROM category WHERE category LIKE :category")
    suspend fun delete(category: String)
}