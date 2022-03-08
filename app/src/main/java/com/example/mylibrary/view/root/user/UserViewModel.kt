package com.example.mylibrary.view.root.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mylibrary.DialogViewModel
import com.example.mylibrary.common.bookInfoToBook
import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.data.entity.firebase.User
import com.example.mylibrary.data.entity.firebase.book.Review
import com.example.mylibrary.data.entity.room.Book
import com.example.mylibrary.data.entity.room.Category
import com.example.mylibrary.data.repository.BookRepository
import com.example.mylibrary.data.repository.CategoryRepository
import com.example.mylibrary.data.repository.FirebaseRepository
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class UserViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val categoryRepository: CategoryRepository,
    private val firebaseRepository: FirebaseRepository
) : DialogViewModel() {

    private val uid = firebaseRepository.getUserAuth()?.uid.orEmpty()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _book = MutableLiveData<List<Book>>()
    val book: LiveData<List<Book>> get() = _book

    private val _category = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>> get() = _category

    private val _bookmarkStatus = MutableLiveData<Boolean?>()
    override val bookmarkStatus: LiveData<Boolean?> get() = _bookmarkStatus

    private val _ratingAverage = MutableLiveData<Float?>()
    override val ratingAverage: LiveData<Float?> get() = _ratingAverage

    private val _review = MutableLiveData<List<Review?>>()
    override val review: LiveData<List<Review?>> get() = _review

////////////////////////////////////////////////////////////////////////////////////////////////////
    fun getMyBook() {
        CoroutineScope(Dispatchers.IO).launch {
            _book.postValue(bookRepository.getMyBook())
        }
    }

    fun getMyCategory() {
        CoroutineScope(Dispatchers.IO).launch {
            _category.postValue(categoryRepository.getMyCategory())
        }
    }

    fun getMyCategoryBook(category: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _book.postValue(bookRepository.getMyCategoryBook(category))
        }
    }

    fun deleteMyCategory(category: String) {
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                categoryRepository.delete(category)
                bookRepository.deleteMyBookCategory(category)
            }.join()
        }
        getMyBook()
        getMyCategory()
    }

    override fun setMyBook(book: Book){
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.insert(book)
        }
    }

    override fun deleteMyBook(isbn: String) {
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                bookRepository.delete(isbn)
            }.join()
        }
        getMyBook()
    }

    override fun setMyCategory(category: Category) {
        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                categoryRepository.insert(category)
            }.join()
        }
        getMyCategory()
    }

////////////////////////////////////////////////////////////////////////////////////////////////////


    fun getUserCategory() {
        val tempList = mutableListOf<Category>()
        firebaseRepository.getCategory().addOnSuccessListener { dataSnapshot ->
            dataSnapshot.children.forEach { category ->
                tempList.add(Category(category.key.toString()))
            }
            _category.postValue(tempList)
        }
    }

    fun getUserBook() {
        val tempList = mutableListOf<Book>()
        firebaseRepository.getAllBook().addOnSuccessListener { dataSnapshot ->
            dataSnapshot.children.forEach { book ->
                tempList.add(bookInfoToBook(book.getValue(BookInfo::class.java)!!))
            }
            _book.postValue(tempList)
        }
    }

    fun deleteUserCategory(category: String){
        firebaseRepository.deleteCategory(category).addOnSuccessListener {
            getUserCategory()
        }
    }

    fun getUserCategoryBook(category: String) {
        val tempList = mutableListOf<Book>()
        firebaseRepository.getCategoryBook(category).addOnSuccessListener { dataSnapshot ->
            dataSnapshot.children.forEach { book ->
                tempList.add(book.getValue(Book::class.java)!!)
            }
            _book.postValue(tempList)
        }
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

    fun getUserInfo() {
        firebaseRepository.getUser().addOnSuccessListener { dataSnapshot ->
            val userInfo = dataSnapshot.getValue(User::class.java)
            _user.postValue(userInfo!!)
        }
    }

    override fun setUserBook(book: BookInfo) {
        firebaseRepository.setBookmark(book)
    }

    override fun deleteUserBook(isbn: String) {
        firebaseRepository.deleteBookmark(isbn)
        getUserBook()
    }

    override fun getRatingAverage(isbn: String) {
        firebaseRepository.getRatingAverage(isbn).addOnSuccessListener { dataSnapshot ->
            if(dataSnapshot.value != null)
                _ratingAverage.postValue(dataSnapshot.value.toString().toFloat())
            else
                _ratingAverage.postValue(0f)
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

    override fun getReview(isbn: String) {
        val tempList = mutableListOf<Review?>()
        firebaseRepository.getReview(isbn).addOnSuccessListener { dataSnapshot ->
            dataSnapshot.children.forEach { review ->
                tempList.add(review.getValue(Review::class.java))
            }
            _review.postValue(tempList)
        }
    }

    override fun getReviewCount(isbn: String) {
        firebaseRepository.getReviewCount(isbn).addOnSuccessListener {

        }
    }

    override fun setReview(bookInfo: BookInfo, content: String) {
        firebaseRepository.setReview(bookInfo,content).addOnSuccessListener {
            getReview(bookInfo.isbn)
        }
    }
}