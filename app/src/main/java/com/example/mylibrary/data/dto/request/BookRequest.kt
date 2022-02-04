package com.example.mylibrary.data.dto.request

import com.example.mylibrary.BuildConfig
import retrofit2.http.Header
import retrofit2.http.Query

data class BookRequest(
    var query: String? = null,
    var display: Int? = null,
    var start: Int? = null,
    var sort: String? = null,
)
