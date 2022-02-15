package com.example.mylibrary.view.root.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mylibrary.data.dto.request.BookRequest
import com.example.mylibrary.data.dto.response.BookInfo
import com.example.mylibrary.data.dto.response.BookResponse
import com.example.mylibrary.data.repository.BookRepository
import com.example.mylibrary.data.repository.NaverRepository
import com.example.mylibrary.data.room.entity.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val naverRepository: NaverRepository,
    private val bookRepository: BookRepository
) : ViewModel() {

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

    fun delete(isbn:String){
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.delete(isbn)
        }
    }

    fun insert(book: Book){
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.insert(book)
        }
    }
}