package com.example.mylibrary.data.entity.firebase.Book

data class Rating(
    val ratingCount: Int,
    val ratedBy: HashMap<String,String>
)
