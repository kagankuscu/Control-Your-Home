package com.kagan.control_your_home.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kagan.control_your_home.R
import com.kagan.control_your_home.databinding.FragmentRoomBinding

class RoomFragment: Fragment(R.layout.fragment_room) {

    val TAG = "RoomFragment"
    lateinit var user: FirebaseUser
lateinit var binding: FragmentRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = FirebaseAuth.getInstance().currentUser!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRoomBinding.bind(view)

        binding.tvName.text = user.email


        binding.cvLivingRoom.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvLivingRoom clicked")
            view.findNavController().navigate(R.id.action_roomFragment_to_roomInsideFragment)
        }

        binding.cvKitchen.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvKitchen clicked")
            view.findNavController().navigate(R.id.action_roomFragment_to_roomInsideFragment)
        }

        binding.cvBedRoom.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvBedRoom clicked")
            view.findNavController().navigate(R.id.action_roomFragment_to_roomInsideFragment)
        }

        binding.cvGuestRoom.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvGuestRoom clicked")
            view.findNavController().navigate(R.id.action_roomFragment_to_roomInsideFragment)
        }


        Log.d(TAG, "Email Verified: ${user.isEmailVerified}")
        Log.d(TAG, "Email: ${user.email}")
        Log.d(TAG, "Name: ${user.displayName}")
        Log.d(TAG, "Number: ${user.phoneNumber}")
        Log.d(TAG, "photo url: ${user.photoUrl}")
    }
}