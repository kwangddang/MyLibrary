package com.example.mylibrary.view.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.util.KotPrefModel
import com.example.mylibrary.util.LoginMethodConstant
import com.example.mylibrary.util.StringConstant
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
): ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> get() = _user

    val email = if(KotPrefModel.loginMethod == LoginMethodConstant.NO_ACCOUNT) StringConstant.NO_ACCOUNT else firebaseAuth.currentUser?.email

    fun logout(){
        KotPrefModel.loginMethod = LoginMethodConstant.NO_ACCOUNT
        firebaseAuth.signOut()
        LoginManager.getInstance().logOut()
        _user.postValue(firebaseAuth.currentUser)
    }
}