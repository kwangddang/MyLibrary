package com.example.mylibrary.view.login.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val passwordVerification = MutableLiveData("")
    val username = MutableLiveData("")

    fun signup(){
        firebaseAuth.createUserWithEmailAndPassword(email.value!!,password.value!!)
            .addOnCompleteListener {
                Log.d("Test",it.result.toString())
            }
    }
}