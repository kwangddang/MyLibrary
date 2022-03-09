package com.example.mylibrary.data.repository

import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.data.entity.room.Book
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query

interface FirebaseRepository {

    fun getUserAuth(): FirebaseUser?

    fun signUp(email: String, password: String, username: String): Task<AuthResult>
    fun signInWithFacebook(credential: AuthCredential): Task<AuthResult>
    fun signInWithEmail(email: String, password: String): Task<AuthResult>

    fun getUsername(): Task<DataSnapshot>
    fun getUser(): Task<DataSnapshot>
    fun setUser(email: String, username: String): Task<Void>
    fun setUsername(username: String): Task<Void>
    fun deleteUser(): Task<Void>

    fun getAllBook(): Task<DataSnapshot>
    fun getCategoryBook(category: String): Task<DataSnapshot>
    fun getPopularBook(): Query

    fun checkCategory(category: String): Task<DataSnapshot>
    fun getCategory(): Task<DataSnapshot>
    fun setCategory(category: String): Task<Void>
    fun setBookCategory(category: String, book: Book)
    fun deleteCategory(category: String): Task<Void>

    fun getBookmarked(isbn: String): Task<DataSnapshot>
    fun getBookmarkCount(isbn: String): Task<DataSnapshot>
    fun setBookmark(bookInfo: BookInfo)
    fun deleteBookmark(isbn: String)

    fun getReview(isbn: String): Task<DataSnapshot>
    fun getReviewCount(isbn: String): Task<DataSnapshot>
    fun setReview(bookInfo: BookInfo, content: String): Task<DataSnapshot>
    fun deleteReview(isbn: String, reviewId: String): Task<Void>

    fun getRatingAverage(isbn: String): Task<DataSnapshot>
    fun setRating(ratingNum: Float, book: BookInfo)
}