package com.example.mylibrary.di

import android.content.Context
import com.example.mylibrary.data.database.BookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideBookDatabase(@ApplicationContext context: Context) :
            BookDatabase = BookDatabase.getInstance(context)
}