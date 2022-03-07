package com.example.mylibrary.view.login.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val passwordVerification = MutableLiveData("")
    val username = MutableLiveData("")

    private val _success = MutableLiveData<Boolean?>(null)
    val success: LiveData<Boolean?> get() = _success

    fun initSuccess(){
        _success.postValue(null)
    }

    fun signup() {
        firebaseRepository.signUp(email.value!!, password.value!!, username.value!!).addOnSuccessListener {
            setUser()
        }.addOnFailureListener {
            _success.postValue(false)
        }
    }

    private fun setUser() {
        firebaseRepository.setUser(email.value!!, username.value!!).addOnSuccessListener {
            _success.postValue(true)
        }.addOnFailureListener {
            _success.postValue(false)
        }
    }
}