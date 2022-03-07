package com.example.mylibrary.view.root.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.entity.room.Book
import com.example.mylibrary.data.repository.BookRepository
import com.example.mylibrary.data.repository.CategoryRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val categoryRepository: CategoryRepository,
    private val firebaseAuth: FirebaseAuth,
    @Named("temp")private val firebaseDB: DatabaseReference
): ViewModel(){

    private val _book = MutableLiveData<List<Book>>()
    val book: LiveData<List<Book>> get() = _book

    fun getMyBook(){
        CoroutineScope(Dispatchers.IO).launch {
            _book.postValue(bookRepository.getMyBook())
        }
    }
}