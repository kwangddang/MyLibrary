package com.example.mylibrary.view.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginEmailViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {


}