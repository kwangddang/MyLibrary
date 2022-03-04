package com.example.mylibrary.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.entity.firebase.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDB: DatabaseReference
) : ViewModel() {

    private val _autoLoginSuccess = MutableLiveData<Boolean>()
    val autoLoginSuccess: LiveData<Boolean> get() = _autoLoginSuccess

    private val _facebookSuccess = MutableLiveData<Boolean?>()
    val facebookSuccess: LiveData<Boolean?> get() = _facebookSuccess

    private val _emailSuccess = MutableLiveData<Boolean?>()
    val emailSuccess: LiveData<Boolean?> get() = _emailSuccess

    private val userId = firebaseAuth.currentUser?.uid.orEmpty()

    val username = MutableLiveData("")

    fun autoLogin(){
        if(firebaseAuth.currentUser != null)
            _autoLoginSuccess.postValue(true)
        else
            _autoLoginSuccess.postValue(false)
    }

    fun facebookLogin(credential: AuthCredential) {
        firebaseAuth.signInWithCredential(credential).addOnSuccessListener {
            checkUsername()
        }.addOnFailureListener {
            _facebookSuccess.postValue(null)
        }
    }

    fun emailLogin(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            _emailSuccess.postValue(true)
        }.addOnFailureListener {
            _emailSuccess.postValue(false)
        }
    }

    fun initSuccessValue(){
        _facebookSuccess.postValue(null)
        _emailSuccess.postValue(null)
    }

    fun setUserInfo(username: String) {
        firebaseDB.child("User").child(userId).setValue(User(firebaseAuth.currentUser?.email,username))
    }

    private fun checkUsername() {
        firebaseDB.child("User").child(userId).child("username").get().addOnSuccessListener {
            _facebookSuccess.postValue(true)
        }.addOnFailureListener {
            _facebookSuccess.postValue(false)
        }
    }
}