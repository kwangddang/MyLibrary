package com.example.mylibrary.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mylibrary.data.api.NaverApi
import com.example.mylibrary.data.dto.BookInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class BookPagingSource (
    private val naverApi: NaverApi,
    private val bookRepository: BookRepository,
    private val query: String
): PagingSource<Int, BookInfo>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookInfo> {
        val position = params.key ?: 0

        return try{
            val response = naverApi.getBook(query = query, display = 10, start = position * 10 + 1, sort = null)

            response.bookInfos.forEach { bookInfo ->
                CoroutineScope(Dispatchers.IO).launch {
                    bookInfo.bookmark = bookInfo.isbn == bookRepository.checkMyBook(bookInfo.isbn)
                }
            }

            val repos = response.bookInfos
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == 0) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BookInfo>): Int? {
        TODO("Not yet implemented")
    }
}