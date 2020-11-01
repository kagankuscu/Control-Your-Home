package com.kagan.control_your_home.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kagan.control_your_home.R
import kotlinx.android.synthetic.main.fragment_room.*

class RoomFragment: Fragment(R.layout.fragment_room) {

    val TAG = "RoomFragment"
    lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = FirebaseAuth.getInstance().currentUser!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Email Verified: ${user.isEmailVerified}")
        Log.d(TAG, "Email: ${user.email}")
        Log.d(TAG, "Name: ${user.displayName}")
        Log.d(TAG, "Number: ${user.phoneNumber}")
        Log.d(TAG, "photo url: ${user.photoUrl}")
    }
}