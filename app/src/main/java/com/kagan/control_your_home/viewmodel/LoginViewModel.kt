package com.kagan.control_your_home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kagan.control_your_home.models.User
import com.kagan.control_your_home.repositries.FirebaseAuthRepository

class LoginViewModel : ViewModel() {

    val TAG = "SmartHomeViewModel"

    private val firebaseAuthRepository = FirebaseAuthRepository()
    lateinit var authUser: LiveData<User>
    lateinit var newUser: LiveData<User>
    lateinit var currentUser: LiveData<User>

    fun getCurrentUser() {
        currentUser = firebaseAuthRepository.getCurrentUser()
    }

    fun signInWithEmail(email: String, password: String) {
        authUser = firebaseAuthRepository.checkAuth(email, password)
    }

    fun createNewAccount(email: String, password: String) {
        newUser = firebaseAuthRepository.createNewAccount(email, password)
    }

    fun updateUserInfo(displayName: String, photoURL: String?) {
        firebaseAuthRepository.updateUserInfo(displayName, photoURL)
    }
}