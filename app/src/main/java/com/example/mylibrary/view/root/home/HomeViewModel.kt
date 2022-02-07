package com.example.mylibrary.view.root.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.dto.request.BookRequest
import com.example.mylibrary.data.dto.response.BookResponse
import com.example.mylibrary.data.repository.BookRepository
import com.example.mylibrary.data.repository.NaverRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val naverRepository: NaverRepository,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _book = MutableLiveData<BookResponse>()
    val book: LiveData<BookResponse> get() = _book

    fun getBook(bookRequest: BookRequest){
        naverRepository.getBook(bookRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response.bookInfos.forEach { bookInfo ->
                    CoroutineScope(Dispatchers.IO).launch {
                        bookInfo.isBookMark = bookInfo.isbn == bookRepository.checkMyBook(bookInfo.isbn)
                    }
                }
                _book.postValue(response)
            },{

            })
    }
}