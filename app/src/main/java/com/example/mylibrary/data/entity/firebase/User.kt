package com.example.mylibrary.data.entity.firebase

data class User(
    val email: String? = null,
    val username: String? = null,
    val category: HashMap<String,String>? = null
)
