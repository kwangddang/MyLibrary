package com.example.mylibrary.data.repository

import com.example.mylibrary.data.entity.room.Category

interface CategoryRepository {

    suspend fun getMyCategory(): List<Category>

    suspend fun insert(category: Category)

    suspend fun delete(category: String)
}