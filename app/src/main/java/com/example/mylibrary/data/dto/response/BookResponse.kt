package com.example.mylibrary.data.dto.response

import com.google.gson.annotations.SerializedName

data class BookResponse(
    val display: Int,
    @SerializedName("items")
    val books: List<Book>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)