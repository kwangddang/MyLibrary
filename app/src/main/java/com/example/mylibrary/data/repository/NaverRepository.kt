package com.example.mylibrary.data.repository

import androidx.paging.PagingData
import com.example.mylibrary.data.dto.BookInfo
import kotlinx.coroutines.flow.Flow

interface NaverRepository {
    fun getBook(queryString: String): Flow<PagingData<BookInfo>>
}