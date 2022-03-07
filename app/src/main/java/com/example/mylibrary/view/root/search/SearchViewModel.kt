package com.example.mylibrary.view.root.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mylibrary.DialogViewModel
import com.example.mylibrary.data.dto.response.BookInfo
import com.example.mylibrary.data.dto.response.BookResponse
import com.example.mylibrary.data.entity.room.Book
import com.example.mylibrary.data.repository.BookRepository
import com.example.mylibrary.data.repository.FirebaseRepository
import com.example.mylibrary.data.repository.NaverRepository
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
    private val firebaseRepository: FirebaseRepository
) : DialogViewModel() {

    private val _bookmarkStatus = MutableLiveData<Boolean?>()
    override val bookmarkStatus: LiveData<Boolean?> get() = _bookmarkStatus

    private val uid = firebaseRepository.getUserAuth()?.uid.orEmpty()

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

    override fun setMyBook(book: Book){
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.insert(book)
        }
    }

    override fun setUserBook(book: BookInfo) {
        firebaseRepository.setBookmark(book)
    }

    override fun deleteUserBook(isbn: String) {
        firebaseRepository.deleteBookmark(isbn)
    }

    fun getBookmarkCount(isbn: String) {
        firebaseRepository.getBookmarkCount(isbn).addOnSuccessListener {

        }
    }

    override fun getBookmarked(isbn: String){
        firebaseRepository.getBookmarked(isbn).addOnSuccessListener { dataSnapshot ->
            if(dataSnapshot.hasChild(uid))
                _bookmarkStatus.postValue(true)
            else
                _bookmarkStatus.postValue(false)
        }.addOnFailureListener{
            _bookmarkStatus.postValue(false)
        }
    }

}