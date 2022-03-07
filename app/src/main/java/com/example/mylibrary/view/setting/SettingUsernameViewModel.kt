package com.example.mylibrary.view.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.data.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SettingUsernameViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): ViewModel(){
    private val _usernameSuccess = MutableLiveData<Boolean?>(null)
    val usernameSuccess: LiveData<Boolean?> get() = _usernameSuccess

    val username = MutableLiveData("")

    fun setUsername(){
        firebaseRepository.setUsername(username.value!!).addOnSuccessListener {
            _usernameSuccess.postValue(true)
        }.addOnFailureListener {
            _usernameSuccess.postValue(false)
        }
    }

    fun initUsername(){
        _usernameSuccess.postValue(null)
        username.postValue("")
    }

}