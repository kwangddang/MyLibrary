package com.example.mylibrary.data.dto

import com.google.gson.annotations.SerializedName

data class BookResponse(
    val display: Int,
    @SerializedName("items")
    val bookInfos: List<BookInfo>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)