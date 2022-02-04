package com.example.mylibrary.data.repository

import com.example.mylibrary.data.api.NaverApi
import com.example.mylibrary.data.dto.request.BookRequest
import com.example.mylibrary.data.dto.response.BookResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NaverRepositoryImpl @Inject constructor(private val naverApi: NaverApi): NaverRepository {
    override fun getBook(bookRequest: BookRequest): Single<BookResponse> = naverApi.getBook(
        query = bookRequest.query,
        display = bookRequest.display,
        start = bookRequest.start,
        sort = bookRequest.sort
    )
}