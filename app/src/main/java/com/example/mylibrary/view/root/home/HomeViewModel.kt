package com.example.mylibrary.view.root.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.dto.BookInfo
import com.example.mylibrary.data.repository.FirebaseRepository
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): ViewModel(){

    private val _book = MutableLiveData<List<BookInfo>>()
    val book: LiveData<List<BookInfo>> get() = _book

    fun getPopularBook(){
        val tempList = mutableListOf<BookInfo>()
        firebaseRepository.getPopularBook().addChildEventListener( object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("Test",snapshot.toString())
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

}