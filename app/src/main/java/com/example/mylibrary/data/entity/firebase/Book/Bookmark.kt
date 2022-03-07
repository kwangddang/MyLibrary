package com.example.mylibrary.data.entity.firebase.book

data class Bookmark(
    val bookmarkCount: Int = 0,
    val bookmarkedBy: HashMap<String,String>
)
