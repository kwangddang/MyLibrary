package com.example.mylibrary.data.dto.request

data class BookRequest(
    var query: String? = null,
    var display: Int? = null,
    var start: Int? = null,
    var sort: String? = null,
)
