package com.example.mylibrary.data.dto.response

data class BookInfo(
    val author: String = "",
    val description: String = "",
    val discount: String = "",
    val image: String = "",
    val isbn: String = "",
    val link: String = "",
    val price: String = "",
    val publisher: String = "",
    val title: String = "",
    var bookmark: Boolean? = null
)