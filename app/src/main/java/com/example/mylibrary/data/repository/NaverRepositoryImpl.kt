package com.example.mylibrary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mylibrary.data.api.NaverApi
import com.example.mylibrary.data.dto.BookInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NaverRepositoryImpl @Inject constructor(
    private val naverApi: NaverApi,
    private  val bookRepository: BookRepository): NaverRepository {
    override fun getBook(queryString: String): Flow<PagingData<BookInfo>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {BookPagingSource(naverApi,bookRepository,queryString)}
        ).flow
}