package com.example.mylibrary.view.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.common.KotPrefModel
import com.example.mylibrary.common.LoginMethodConstant
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
): ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> get() = _user

    val email = if(KotPrefModel.loginMethod == LoginMethodConstant.NO_ACCOUNT) "비계정" else firebaseAuth.currentUser?.email

    fun logout(){
        KotPrefModel.loginMethod = LoginMethodConstant.NO_ACCOUNT
        firebaseAuth.signOut()
        LoginManager.getInstance().logOut()
        _user.postValue(firebaseAuth.currentUser)
    }
}