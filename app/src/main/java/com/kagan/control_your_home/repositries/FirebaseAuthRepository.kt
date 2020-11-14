package com.kagan.control_your_home.repositries

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.kagan.control_your_home.models.User

class FirebaseAuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun getCurrentUser(): MutableLiveData<User> {
        val currentUser = MutableLiveData<User>()
        firebaseAuth.currentUser?.let {
            val uid = it.uid
            val userName = it.displayName
            val email = it.email
            currentUser.value = User(uid, userName, email!!)
        }

        return currentUser
    }

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

    fun updateUserInfo(name: String, photo: String?) {
        val user = firebaseAuth.currentUser
        val profileChangeRequest = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .setPhotoUri(Uri.parse(photo))
            .build()

        user!!.updateProfile(profileChangeRequest)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    Log.d("Login", "updateUserInfo: ")
            }
    }
}