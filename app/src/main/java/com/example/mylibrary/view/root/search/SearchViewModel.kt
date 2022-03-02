package com.example.mylibrary.view.root.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mylibrary.BaseViewModel
import com.example.mylibrary.data.dto.response.BookInfo
import com.example.mylibrary.data.dto.response.BookResponse
import com.example.mylibrary.data.entity.firebase.Book.UserBook
import com.example.mylibrary.data.repository.BookRepository
import com.example.mylibrary.data.repository.NaverRepository
import com.example.mylibrary.data.entity.room.Book
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val naverRepository: NaverRepository,
    private val bookRepository: BookRepository,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDB: DatabaseReference
) : BaseViewModel() {

    private val userId = firebaseAuth.currentUser?.uid.orEmpty()

    private val _book = MutableLiveData<BookResponse>()
    val book: LiveData<BookResponse> get() = _book

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<BookInfo>>? = null

    fun getBook(queryString: String): Flow<PagingData<BookInfo>>{
        val lastResult = currentSearchResult
        if(queryString == currentQueryValue && lastResult != null){
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<BookInfo>> = naverRepository.getBook(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    override fun deleteMyBook(isbn:String){
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.delete(isbn)
        }
    }

    override fun insertMyBook(book: Book){
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.insert(book)
        }
    }

    override fun insertUserBook(book: UserBook) {
        //        val book = mutableMapOf<String,Any>()
//        book["블랭"] = "19970411"
//        db.updateChildren(book)
        val db = firebaseDB.child("Book").child(book.isbn).child("Bookmark")
        val bookmarkCount = mutableMapOf<String,Any>()
        bookmarkCount["bookmarkCount"] = book.bookmark["bookmarkCount"]?.bookmarkCount!! + 1
        val bookmarkedBy = mutableMapOf<String,Any>()
        bookmarkedBy["bookmarkedBy"] = userId
        db.updateChildren(bookmarkCount)
        db.updateChildren(bookmarkedBy)
    }

    override fun deleteUserBook(isbn: String) {
        TODO("Not yet implemented")
    }

}