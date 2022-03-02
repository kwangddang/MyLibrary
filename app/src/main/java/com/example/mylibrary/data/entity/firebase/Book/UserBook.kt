package com.example.mylibrary.data.entity.firebase.Book

data class UserBook(
    val author: String,
    val description: String,
    val discount: String,
    val image: String,
    val isbn: String,
    val link: String,
    val price: String,
    val pubdate: String,
    val publisher: String,
    val title: String,
    val bookmark: HashMap<String, Bookmark>,
    val rating: HashMap<String,Rating>
)
