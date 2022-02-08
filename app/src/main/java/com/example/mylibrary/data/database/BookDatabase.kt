package com.example.mylibrary.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mylibrary.data.room.dao.BookDao
import com.example.mylibrary.data.room.dao.CategoryDao
import com.example.mylibrary.data.room.entity.Book
import com.example.mylibrary.data.room.entity.Category

@Database(entities = [Book::class, Category::class], version = 1)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        fun getInstance(context: Context): BookDatabase =
            Room.databaseBuilder(context, BookDatabase::class.java, "book_db").build()
    }
}