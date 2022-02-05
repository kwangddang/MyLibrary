package com.example.mylibrary.di

import com.example.mylibrary.data.database.BookDatabase
import com.example.mylibrary.data.room.dao.BookDao
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
        fun providePictureDao(bookDatabase: BookDatabase): BookDao =
            bookDatabase.bookDao()
    }
}