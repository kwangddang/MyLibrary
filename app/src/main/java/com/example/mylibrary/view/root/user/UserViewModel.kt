package com.example.mylibrary.view.root.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.firebase.User
import com.example.mylibrary.data.repository.BookRepository
import com.example.mylibrary.data.repository.CategoryRepository
import com.example.mylibrary.data.room.entity.Book
import com.example.mylibrary.data.room.entity.Category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val categoryRepository: CategoryRepository,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDB: DatabaseReference
): ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _book = MutableLiveData<List<Book>>()
    val book: LiveData<List<Book>> get() = _book

    private val _category = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>> get() = _category

    fun getMyBook(){
        CoroutineScope(Dispatchers.IO).launch {
            _book.postValue(bookRepository.getMyBook())
        }
    }

    fun getCategoryBook(category: String){
        CoroutineScope(Dispatchers.IO).launch {
            _book.postValue(bookRepository.getCategoryBook(category))
        }
    }

    fun deleteBook(isbn:String){
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.delete(isbn)
        }
    }

    fun getCategory(){
        CoroutineScope(Dispatchers.IO).launch {
            _category.postValue(categoryRepository.getCategory())
        }
    }

    fun deleteCategory(category: String){
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                categoryRepository.delete(category)
                bookRepository.deleteBookCategory(category)
            }.join()
        }
        getMyBook()
        getCategory()
    }

    fun insertCategory(category: Category){
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                categoryRepository.insert(category)
            }.join()
        }
        getCategory()
    }

    fun getUser() {
        val userId = firebaseAuth.currentUser?.uid.orEmpty()

        firebaseDB.child("User").child(userId).get().addOnSuccessListener {
            var email: String? = null
            var username: String? = null
            it.children.forEach { data ->
                if(data.key == "email") email = data.value.toString()
                else if(data.key == "username") username = data.value.toString()
            }
            _user.postValue(User(email,username))
        }
    }

}