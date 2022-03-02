package com.example.mylibrary.data.repository

import com.example.mylibrary.data.dao.CategoryDao
import com.example.mylibrary.data.entity.room.Category
import javax.inject.Inject

class CategoryRepositoryImpl@Inject constructor(private val categoryDao: CategoryDao): CategoryRepository {
    override suspend fun getMyCategory(): List<Category> = categoryDao.getMyCategory()

    override suspend fun insert(category: Category) = categoryDao.insert(category)

    override suspend fun delete(category: String)  = categoryDao.delete(category)

}