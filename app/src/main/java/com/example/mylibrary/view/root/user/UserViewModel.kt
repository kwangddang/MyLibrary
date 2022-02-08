package com.example.mylibrary.view.root.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.repository.BookRepository
import com.example.mylibrary.data.repository.CategoryRepository
import com.example.mylibrary.data.room.entity.Book
import com.example.mylibrary.data.room.entity.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val categoryRepository: CategoryRepository
): ViewModel() {

    private val _book = MutableLiveData<List<Book>>()
    val book: LiveData<List<Book>> get() = _book

    private val _category = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>> get() = _category

    fun getMyBook(){
        CoroutineScope(Dispatchers.IO).launch {
            _book.postValue(bookRepository.getMyBook())
        }
    }

    fun deleteBook(isbn:String){
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.delete(isbn)
        }
    }

    fun insertBook(book: Book){
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.insert(book)
        }
    }

    fun getCategory(){
        CoroutineScope(Dispatchers.IO).launch {
            _category.postValue(categoryRepository.getCategory())
        }
    }

    fun deleteCategory(category: String){
        CoroutineScope(Dispatchers.IO).launch {
            categoryRepository.delete(category)
        }
    }

    fun insertCategory(category: Category){
        CoroutineScope(Dispatchers.IO).launch {
            categoryRepository.insert(category)
        }
    }
}