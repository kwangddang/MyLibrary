package com.example.mylibrary.data.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mylibrary.data.room.entity.Category

interface CategoryRepository {

    suspend fun getCategory(): List<Category>

    suspend fun insert(category: Category)

    suspend fun delete(category: String)
}