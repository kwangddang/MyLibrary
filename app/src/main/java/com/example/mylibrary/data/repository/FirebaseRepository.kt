package com.example.mylibrary.data.repository

import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.data.entity.room.Book
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot

interface FirebaseRepository {

    fun getUserAuth(): FirebaseUser?

    fun getBookmarkCount(isbn: String): Task<DataSnapshot>
    fun getRatingAverage(isbn: String): Task<DataSnapshot>
    fun getBookmarked(isbn: String): Task<DataSnapshot>
    fun getAllBook(): Task<DataSnapshot>
    fun getCategoryBook(category: String): Task<DataSnapshot>
    fun getReview(isbn: String): Task<DataSnapshot>
    fun getReviewCount(isbn: String): Task<DataSnapshot>
    fun setReview(bookInfo: BookInfo, content: String): Task<DataSnapshot>
    fun setRating(ratingNum: Float, book: BookInfo)
    fun setBookmark(bookInfo: BookInfo)
    fun deleteBookmark(isbn: String)

    fun signUp(email: String, password: String, username: String): Task<AuthResult>
    fun signInWithFacebook(credential: AuthCredential): Task<AuthResult>
    fun signInWithEmail(email: String, password: String): Task<AuthResult>
    fun getUsername(): Task<DataSnapshot>
    fun getUser(): Task<DataSnapshot>
    fun setUser(email: String, username: String): Task<Void>
    fun setUsername(username: String): Task<Void>
    fun deleteUser(): Task<Void>

    fun checkCategory(category: String): Task<DataSnapshot>
    fun getCategory(): Task<DataSnapshot>
    fun deleteCategory(category: String): Task<Void>
    fun setCategory(category: String): Task<Void>
    fun setBookCategory(category: String, book: Book)
}