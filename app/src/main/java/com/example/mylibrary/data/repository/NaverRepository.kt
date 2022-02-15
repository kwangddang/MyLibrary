package com.example.mylibrary.data.repository

import androidx.paging.PagingData
import com.example.mylibrary.data.dto.request.BookRequest
import com.example.mylibrary.data.dto.response.BookInfo
import com.example.mylibrary.data.dto.response.BookResponse
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface NaverRepository {
    fun getBook(queryString: String): Flow<PagingData<BookInfo>>
}