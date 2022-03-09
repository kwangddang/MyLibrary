package com.example.mylibrary.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.repository.FirebaseRepository
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
) : ViewModel() {

    private val _autoLoginSuccess = MutableLiveData<Boolean>()
    val autoLoginSuccess: LiveData<Boolean> get() = _autoLoginSuccess

    private val _facebookLoginSuccess = MutableLiveData<Boolean?>()
    val facebookLoginSuccess: LiveData<Boolean?> get() = _facebookLoginSuccess

    private val _emailLoginSuccess = MutableLiveData<Boolean?>()
    val emailLoginSuccess: LiveData<Boolean?> get() = _emailLoginSuccess

    private val _facebookUsernameSuccess = MutableLiveData<Boolean?>()
    val facebookUsernameSuccess: LiveData<Boolean?> get() = _facebookUsernameSuccess

    val username = MutableLiveData("")

    fun autoLogin(){
        if(firebaseRepository.getUserAuth() != null)
            _autoLoginSuccess.postValue(true)
        else
            _autoLoginSuccess.postValue(false)
    }

    fun facebookLogin(credential: AuthCredential) {
        firebaseRepository.signInWithFacebook(credential).addOnSuccessListener {
            getUsername()
        }.addOnFailureListener {
            _facebookLoginSuccess.postValue(null)
        }
    }

    private fun getUsername() {
        firebaseRepository.getUsername().addOnSuccessListener { dataSnapshot ->
            if(dataSnapshot.value == null)
                _facebookLoginSuccess.postValue(false)
            else
                _facebookLoginSuccess.postValue(true)
        }
    }

    fun emailLogin(email: String, password: String){
        if(email.isNotEmpty() && password.isNotEmpty()) {
            firebaseRepository.signInWithEmail(email, password).addOnSuccessListener {
                _emailLoginSuccess.postValue(true)
            }.addOnFailureListener {
                _emailLoginSuccess.postValue(false)
            }
        }
    }

    fun initSuccessValue(){
        _facebookLoginSuccess.postValue(null)
        _emailLoginSuccess.postValue(null)
        _facebookUsernameSuccess.postValue(null)
    }

    fun setUserInfo(username: String) {
        firebaseRepository.setUser(firebaseRepository.getUserAuth()?.email!!,username).addOnSuccessListener {
            _facebookUsernameSuccess.postValue(true)
        }.addOnFailureListener {
            _facebookUsernameSuccess.postValue(false)
        }
    }

}