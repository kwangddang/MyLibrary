package com.example.mylibrary.view.root.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.entity.firebase.User
import com.example.mylibrary.data.repository.BookRepository
import com.example.mylibrary.data.repository.CategoryRepository
import com.example.mylibrary.data.entity.room.Book
import com.example.mylibrary.data.entity.room.Category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
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

    private val _myCategory = MutableLiveData<List<Category>>()
    val myCategory: LiveData<List<Category>> get() = _myCategory

    private val _userCategory = MutableLiveData<List<Category>>()
    val userCategory: LiveData<List<Category>> get() = _userCategory

    private val userId = firebaseAuth.currentUser?.uid.orEmpty()

    fun getMyBook(){
        CoroutineScope(Dispatchers.IO).launch {
            _book.postValue(bookRepository.getMyBook())
        }
    }

    fun getMyCategory(){
        CoroutineScope(Dispatchers.IO).launch {
            categoryRepository.getMyCategory().forEach {
                firebaseDB.child("Category").child(it.category).setValue(it)
            }
            _myCategory.postValue(categoryRepository.getMyCategory())
        }
    }

    fun getMyCategoryBook(category: String){
        CoroutineScope(Dispatchers.IO).launch {
            _book.postValue(bookRepository.getMyCategoryBook(category))
        }
    }

    fun deleteMyBook(isbn:String){
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.delete(isbn)
        }
    }

    fun deleteMyCategory(category: String){
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                categoryRepository.delete(category)
                bookRepository.deleteMyBookCategory(category)
            }.join()
        }
        getMyBook()
        getMyCategory()
    }

    fun insertMyCategory(category: Category){
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                categoryRepository.insert(category)
            }.join()
        }
        getMyCategory()
    }


    fun getUserCategory(){
        val tempList = mutableListOf<Category>()
//        val book = mutableMapOf<String,Any>()
//        book["블랭"] = "19970411"
//        db.updateChildren(book)
        firebaseDB.child("User").child(userId).child("category").get().addOnCompleteListener { task ->
            task.result.children.forEach { category ->
                tempList.add(Category(category.key.toString()))
            }
            _userCategory.postValue(tempList)
        }
    }

    fun getUserBook(){
        firebaseDB.child("User").child(userId).child("category").get().addOnCompleteListener { task ->
            task.result.children.forEach { book ->
                Log.d("Test",book.key.toString())
                Log.d("Test",book.value.toString())
            }
        }
    }

    fun getUserCategoryBook(category: String){
        firebaseDB.child("User").child(userId).child("category").child(category).get().addOnSuccessListener {
            Log.d("Test",it.toString())
            Log.d("Test",it.children.toString())
            Log.d("Test",it.key.toString())
            Log.d("Test",it.value.toString())
        }

        firebaseDB.child("User").child(userId).child("category").child(category).get().addOnCompleteListener {

        }
    }

    fun setUserBookCategory(){

    }


    fun getUserInfo() {
        firebaseDB.child("User").child(userId).get().addOnSuccessListener { data ->
            val userInfo = data.getValue(User::class.java)
            Log.d("Test",userInfo.toString())
            _user.postValue(userInfo!!)
        }
    }
}