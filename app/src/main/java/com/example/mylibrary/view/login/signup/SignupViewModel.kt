package com.example.mylibrary.view.login.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.entity.firebase.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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

    private val _success = MutableLiveData<Any?>()
    val success: LiveData<Any?> get() = _success

    fun signup() {
        firebaseAuth.createUserWithEmailAndPassword(email.value!!, password.value!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    successSignup()
                } else {
                    _success.postValue(null)
                }
            }
    }

    private fun successSignup() {
        val userId = firebaseAuth.currentUser?.uid.orEmpty()
        Firebase.database.reference.child("User").child(userId)
            .setValue(User(email.value, username.value)).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _success.postValue(task)
            } else {
                _success.postValue(null)
            }
        }
    }
}