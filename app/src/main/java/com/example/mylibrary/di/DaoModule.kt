package com.example.mylibrary.di

import com.example.mylibrary.data.database.BookDatabase
import com.example.mylibrary.data.dao.BookDao
import com.example.mylibrary.data.dao.CategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DaoModule {
    companion object {

        @Singleton
        @Provides
        fun provideBookDao(bookDatabase: BookDatabase): BookDao =
            bookDatabase.bookDao()

        @Singleton
        @Provides
        fun provideCategoryDao(bookDatabase: BookDatabase): CategoryDao =
            bookDatabase.categoryDao()
    }
}