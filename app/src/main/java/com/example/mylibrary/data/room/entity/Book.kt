package com.example.mylibrary.data.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Book(
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "discount") val discount: String,
    @ColumnInfo(name = "image") val image: String,
    @PrimaryKey
    @ColumnInfo(name = "isbn")val isbn: String,
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "pubdate")val pubdate: String,
    @ColumnInfo(name = "publisher") val publisher: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "bookmark") val isBookMark: Boolean?
) : Parcelable
