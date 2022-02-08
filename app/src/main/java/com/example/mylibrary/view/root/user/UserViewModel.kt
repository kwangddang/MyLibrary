package com.example.mylibrary.view.root.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.repository.BookRepository
import com.example.mylibrary.data.room.entity.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val bookRepository: BookRepository
): ViewModel() {
    private val _book = MutableLiveData<List<Book>>()
    val book: LiveData<List<Book>> get() = _book

    fun getMyBook(){
        CoroutineScope(Dispatchers.IO).launch {
            _book.postValue(bookRepository.getMyBook())
        }
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