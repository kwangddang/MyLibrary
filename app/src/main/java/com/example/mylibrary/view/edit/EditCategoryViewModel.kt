package com.example.mylibrary.view.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.DialogViewModel
import com.example.mylibrary.data.entity.room.Book
import com.example.mylibrary.data.entity.room.Category
import com.example.mylibrary.data.repository.BookRepository
import com.example.mylibrary.data.repository.CategoryRepository
import com.example.mylibrary.data.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class EditCategoryViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val categoryRepository: CategoryRepository,
    private val firebaseRepository: FirebaseRepository
): DialogViewModel() {
    private val _category = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>> get() = _category

    fun getMyCategory(){
        CoroutineScope(Dispatchers.IO).launch {
            _category.postValue(categoryRepository.getMyCategory())
        }
    }

    fun setMyBookCategory(category: String, isbn: String){
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.setMyBookCategory(category, isbn)
        }
    }

    override fun setMyCategory(category: Category) {
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                categoryRepository.insert(category)
            }.join()
        }
        getMyCategory()
    }

    fun getUserCategory() {
        val tempList = mutableListOf<Category>()
        firebaseRepository.getCategory().addOnSuccessListener { dataSnapshot ->
            dataSnapshot.children.forEach { category ->
                tempList.add(Category(category.key.toString()))
            }
            _category.postValue(tempList)
        }
    }

    fun setUserBookCategory(category: String, book: Book){
        firebaseRepository.setBookCategory(category,book)
    }

    override fun setUserCategory(category: String) {
        firebaseRepository.checkCategory(category).addOnSuccessListener { dataSnapshot ->
            if (!dataSnapshot.hasChild(category)) {
                firebaseRepository.setCategory(category).addOnSuccessListener {
                    getUserCategory()
                }
            }
        }
    }
}