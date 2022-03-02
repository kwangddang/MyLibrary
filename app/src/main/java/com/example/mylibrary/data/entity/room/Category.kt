package com.example.mylibrary.data.entity.room

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull

@Entity
@Parcelize
data class Category(
    @PrimaryKey
    @ColumnInfo(name = "category") val category: String,
) : Parcelable
