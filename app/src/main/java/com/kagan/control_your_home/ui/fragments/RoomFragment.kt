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
import com.kagan.control_your_home.viewmodel.LoginViewModel

class RoomFragment : Fragment(R.layout.fragment_room) {

    val TAG = "RoomFragment"
    lateinit var binding: FragmentRoomBinding
    private val dbViewModel: DBViewModel by viewModels()
    private val authViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRoomBinding.bind(view)

        setInfo()
        setCurrentUser()

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

    private fun setCurrentUser() {
        authViewModel.getCurrentUser()
        authViewModel.currentUser.observe(viewLifecycleOwner, Observer {
            if (it.userName == null) {
                binding.tvName.text = it.email
            } else {
                if (it.userName.isNotEmpty()) {
                    binding.tvName.text = it.userName
                } else {
                    binding.tvName.text = it.email
                }
            }
        })
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