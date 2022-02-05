package com.example.mylibrary.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mylibrary.data.room.dao.BookDao
import com.example.mylibrary.data.room.entity.Book

@Database(entities = [Book::class], version = 1)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        fun getInstance(context: Context): BookDatabase =
            Room.databaseBuilder(context, BookDatabase::class.java, "book_db").build()
    }
}