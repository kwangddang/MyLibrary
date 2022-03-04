package com.example.mylibrary.view.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingUsernameViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDB: DatabaseReference
): ViewModel(){
    private val _usernameSuccess = MutableLiveData<Boolean?>(null)
    val usernameSuccess: LiveData<Boolean?> get() = _usernameSuccess

    private val userId = firebaseAuth.currentUser?.uid.orEmpty()

    val username = MutableLiveData("")

    fun setUsername(){
        val db = firebaseDB.child("User").child(userId)
        val change = mutableMapOf<String,Any>()
        change["username"] = username.value!!
        db.updateChildren(change).addOnSuccessListener {
            _usernameSuccess.postValue(true)
        }.addOnFailureListener {
            _usernameSuccess.postValue(false)
        }
    }

    fun initUsername(){
        _usernameSuccess.postValue(null)
        username.postValue("")
    }

}