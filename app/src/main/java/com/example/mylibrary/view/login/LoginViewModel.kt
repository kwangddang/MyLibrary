package com.example.mylibrary.view.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    fun facebookLogin(credential: AuthCredential){
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){

                } else{
                    Log.d("Test",task.result.toString())
                }
            }
    }

    fun successLogin(){
        if(firebaseAuth.currentUser == null){
            Log.d("Test","로그인 실패")
            return
        }
        val userId = firebaseAuth.currentUser?.uid.orEmpty()
        val currentUserDB = Firebase.database.reference.child("User").child(userId)
        val user = mutableMapOf<String,Any>()
        user["userId"] = userId
        currentUserDB.updateChildren(user)
    }
}