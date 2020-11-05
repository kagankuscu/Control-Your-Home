package com.kagan.control_your_home.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.kagan.control_your_home.R
import com.kagan.control_your_home.databinding.FragmentRoomInsideBinding

class RoomInsideFragment : Fragment(R.layout.fragment_room_inside) {

    val TAG = "RoomInsideFragment"
    lateinit var binding: FragmentRoomInsideBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRoomInsideBinding.bind(view)
        Log.d(TAG, TAG)


        binding.ivBack.setOnClickListener {
            view.findNavController().navigate(R.id.action_roomInsideFragment_to_roomFragment2)
        }

        binding.cvLamp.setOnClickListener {
            Log.d(TAG, "onViewCreated: ghd")
        }

        binding.cvLamp.setOnLongClickListener {
            view.findNavController().navigate(R.id.action_roomInsideFragment_to_deviceFragment2)
            return@setOnLongClickListener true
        }

        binding.cvFan.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvfan")
        }

        binding.cvFan.setOnLongClickListener {
            view.findNavController().navigate(R.id.action_roomInsideFragment_to_deviceFragment2)
            return@setOnLongClickListener true
        }

        binding.cvTV.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvTV")
        }

        binding.cvTV.setOnLongClickListener {
            view.findNavController().navigate(R.id.action_roomInsideFragment_to_deviceFragment2)
            return@setOnLongClickListener true
        }

        binding.cvMotionSensor.setOnClickListener {
            Log.d(TAG, "onViewCreated: cvMotion")
        }

        binding.cvMotionSensor.setOnLongClickListener {
            view.findNavController().navigate(R.id.action_roomInsideFragment_to_deviceFragment2)
            return@setOnLongClickListener true
        }

    }
}