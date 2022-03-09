package com.example.mylibrary.view.root.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mylibrary.view.common.DialogViewModel
import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.data.entity.firebase.Review
import com.example.mylibrary.data.repository.FirebaseRepository
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): DialogViewModel(){

    private val _book = MutableLiveData<List<BookInfo>>()
    val book: LiveData<List<BookInfo>> get() = _book

    private val _bookmarkStatus = MutableLiveData<Boolean?>()
    override val bookmarkStatus: LiveData<Boolean?> get() = _bookmarkStatus

    private val _ratingAverage = MutableLiveData<Float?>()
    override val ratingAverage: LiveData<Float?> get() = _ratingAverage

    private val _review = MutableLiveData<List<Review?>>()
    override val review: LiveData<List<Review?>> get() = _review

    override val uid = firebaseRepository.getUserAuth()?.uid.orEmpty()

    fun getPopularBook(){
        val tempList = mutableListOf<BookInfo>()
        firebaseRepository.getPopularBook().addChildEventListener( object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if(tempList.size > 8) tempList.removeAt(0)
                tempList.add(snapshot.getValue(BookInfo::class.java)!!)
                _book.postValue(tempList)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        )
    }

    override fun setUserBook(book: BookInfo) {
        firebaseRepository.setBookmark(book)
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

    override fun setBookRating(ratingNum: Float, book: BookInfo) {
        firebaseRepository.setRating(ratingNum, book)
    }

    override fun getRatingAverage(isbn: String) {
        firebaseRepository.getRatingAverage(isbn).addOnSuccessListener { dataSnapshot ->
            if(dataSnapshot.value != null)
                _ratingAverage.postValue(dataSnapshot.value.toString().toFloat())
            else
                _ratingAverage.postValue(0f)
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

    override fun setReview(bookInfo: BookInfo, content: String) {
        firebaseRepository.setReview(bookInfo,content).addOnSuccessListener {
            getReview(bookInfo.isbn)
        }
    }

    override fun deleteReview(isbn: String, reviewId: String) {
        firebaseRepository.deleteReview(isbn,reviewId).addOnSuccessListener {
            getReview(isbn)
        }
    }


}