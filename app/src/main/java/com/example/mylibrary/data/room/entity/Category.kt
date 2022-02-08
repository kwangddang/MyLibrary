package com.example.mylibrary.data.room.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Category(
    @PrimaryKey
    @ColumnInfo(name = "category") val category: String
)
