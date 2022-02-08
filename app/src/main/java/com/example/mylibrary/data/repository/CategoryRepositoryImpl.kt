package com.example.mylibrary.data.repository

import com.example.mylibrary.data.room.dao.BookDao
import com.example.mylibrary.data.room.dao.CategoryDao
import com.example.mylibrary.data.room.entity.Category
import javax.inject.Inject

class CategoryRepositoryImpl@Inject constructor(private val categoryDao: CategoryDao): CategoryRepository {
    override suspend fun getCategory(): List<Category> = categoryDao.getCategory()

    override suspend fun insert(category: Category) = categoryDao.insert(category)

    override suspend fun delete(category: String) = categoryDao.delete(category)
}