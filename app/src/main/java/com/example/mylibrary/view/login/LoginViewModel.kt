package com.example.mylibrary.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.firebase.User
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

    private val _facebookSuccess = MutableLiveData<Any?>()
    val facebookSuccess: LiveData<Any?> get() = _facebookSuccess

    private val _emailSuccess = MutableLiveData<Any?>()
    val emailSuccess: LiveData<Any?> get() = _emailSuccess

    val username = MutableLiveData("")

    fun facebookLogin(credential: AuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    checkUsername()
                } else {
                    _facebookSuccess.postValue(null)
                }
            }
    }

    fun emailLogin(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    _emailSuccess.postValue(task)
                } else{
                    _emailSuccess.postValue(null)
                }
            }
    }

    fun setUserInfo(username: String) {
        val userId = firebaseAuth.currentUser?.uid.orEmpty()
        firebaseDB.child("User").child(userId).setValue(User(firebaseAuth.currentUser?.email,username))
    }

    private fun checkUsername() {
        val userId = firebaseAuth.currentUser?.uid.orEmpty()
        firebaseDB.child("User").child(userId).child("username").get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                _facebookSuccess.postValue(task)
            } else{
                _facebookSuccess.postValue(null)
            }
        }
    }
}