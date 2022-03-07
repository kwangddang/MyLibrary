package com.example.mylibrary.data.entity.firebase.book

data class Rating(
    val ratingCount: Int,
    val ratedBy: HashMap<String,String>
)
