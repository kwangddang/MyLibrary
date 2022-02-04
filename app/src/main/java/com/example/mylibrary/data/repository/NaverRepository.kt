package com.example.mylibrary.data.repository

import com.example.mylibrary.data.dto.request.BookRequest
import com.example.mylibrary.data.dto.response.BookResponse
import io.reactivex.rxjava3.core.Single

interface NaverRepository {
    fun getBook(bookRequest: BookRequest): Single<BookResponse>
}