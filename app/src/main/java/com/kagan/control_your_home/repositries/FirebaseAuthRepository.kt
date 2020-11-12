package com.kagan.control_your_home.repositries

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kagan.control_your_home.models.User

class FirebaseAuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun checkAuth(email: String, password: String): MutableLiveData<User> {
        val authUser = MutableLiveData<User>()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseAuth.currentUser?.let {
                        val uId = it.uid
                        val userName = it.displayName
                        val userEmail = it.email
                        authUser.value = User(uId, userName, userEmail!!)
                    }
                } else {
                    Log.d("Login", "checkAuth: ${task.exception?.message}")
                }
            }
        return authUser
    }

    fun createNewAccount(email: String, password: String): MutableLiveData<User> {
        val createUser = MutableLiveData<User>()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseAuth.currentUser?.let {
                        val uId = it.uid
                        val userName = it.displayName
                        val userEmail = it.email
                        createUser.value = User(uId, userName, userEmail!!)
                    }
                } else {
                    Log.d("Login", "createAccount: ${task.exception?.message}")
                }
            }
        return createUser
    }
}