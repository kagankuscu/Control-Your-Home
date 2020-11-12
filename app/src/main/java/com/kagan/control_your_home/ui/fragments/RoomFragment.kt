package com.kagan.control_your_home.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.kagan.control_your_home.R
import com.kagan.control_your_home.databinding.FragmentRoomBinding
import com.kagan.control_your_home.viewmodel.DBViewModel

class RoomFragment : Fragment(R.layout.fragment_room) {

    val TAG = "RoomFragment"
    lateinit var user: FirebaseUser
    lateinit var binding: FragmentRoomBinding
    private val dbViewModel: DBViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = FirebaseAuth.getInstance().currentUser!!
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRoomBinding.bind(view)

        setInfo()

        binding.tvName.text = user.email

        binding.cvLivingRoom.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvLivingRoom clicked")
            navigate(getString(R.string.living_room))
        }

        binding.cvKitchen.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvKitchen clicked")
            navigate(getString(R.string.kitchen))
        }

        binding.cvBedRoom.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvBedRoom clicked")
            navigate(getString(R.string.bed_room))
        }

        binding.cvGuestRoom.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvGuestRoom clicked")
            navigate(getString(R.string.guest_room))
        }
    }

    private fun logUserInfo() {
        Log.d(TAG, "Email Verified: ${user.isEmailVerified}")
        Log.d(TAG, "Email: ${user.email}")
        Log.d(TAG, "Name: ${user.displayName}")
        Log.d(TAG, "Number: ${user.phoneNumber}")
        Log.d(TAG, "photo url: ${user.photoUrl}")
    }

    private fun navigate(value: String) {
        val action = RoomFragmentDirections.actionRoomFragmentToRoomInsideFragment(value)
        findNavController().navigate(action)
    }

    private fun setInfo() {
        dbViewModel.getInfo()
        dbViewModel.info.observe(viewLifecycleOwner, Observer {
            binding.tvHumidity.text = getString(R.string.info_hum, it.hum)
            binding.tvLum.text = getString(R.string.info_lum, it.lum)
            binding.tvTemp.text = getString(R.string.info_temp, it.temp)
        })
    }
}