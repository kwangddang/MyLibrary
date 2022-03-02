package com.example.mylibrary.view.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.repository.BookRepository
import com.example.mylibrary.data.repository.CategoryRepository
import com.example.mylibrary.data.entity.room.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class EditCategoryViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val categoryRepository: CategoryRepository
): ViewModel() {
    private val _category = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>> get() = _category

    fun getCategory(){
        CoroutineScope(Dispatchers.IO).launch {
            _category.postValue(categoryRepository.getMyCategory())
        }
    }

    fun setBookCategory(category: String, isbn: String){
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.setMyBookCategory(category, isbn)
        }
    }

    fun insertCategory(category: Category){
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                categoryRepository.insert(category)
            }.join()
        }
        getCategory()
    }
}