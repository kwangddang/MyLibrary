package com.example.mylibrary

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.data.entity.firebase.book.Review
import com.example.mylibrary.data.entity.room.Book
import com.example.mylibrary.data.entity.room.Category
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

open class DialogViewModel: ViewModel() {
    open val review: LiveData<List<Review?>> = liveData {  }
    open val ratingAverage: LiveData<Float?> = liveData {  }
    open val bookmarkStatus: LiveData<Boolean?> = liveData {  }
    open fun setMyBook(book: Book){}
    open fun deleteMyBook(isbn:String){}
    open fun setUserBook(book: BookInfo){}
    open fun deleteUserBook(isbn:String){}
    open fun getBookmarked(isbn: String){}
    open fun setMyCategory(category: Category){}
    open fun setUserCategory(category: String){}
    open fun setBookRating(ratingNum: Float, book: BookInfo){}
    open fun getRatingAverage(isbn: String){}
    open fun getReview(isbn: String){}
    open fun getReviewCount(isbn: String){}
    open fun setReview(bookInfo: BookInfo, content: String){}
}