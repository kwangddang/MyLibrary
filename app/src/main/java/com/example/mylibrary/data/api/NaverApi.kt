package com.example.mylibrary.data.api

import com.example.mylibrary.BuildConfig
import com.example.mylibrary.data.dto.response.BookResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverApi {

    @GET("search/book.json")
    suspend fun getBook(
        @Header("X-Naver-Client-Id") clientId: String? = BuildConfig.NAVER_CLIENT_ID,
        @Header("X-Naver-Client-Secret") clientPw: String? = BuildConfig.NAVER_CLIENT_SECRET,
        @Query("query") query: String?,
        @Query("display") display: Int?,
        @Query("start") start: Int?,
        @Query("sort") sort: String?
    ): BookResponse

    companion object{
        fun create(retrofitBuilder: Retrofit.Builder): NaverApi{
            return retrofitBuilder
                .baseUrl(BuildConfig.BASE_URL)
                .build()
                .create()
        }
    }
}