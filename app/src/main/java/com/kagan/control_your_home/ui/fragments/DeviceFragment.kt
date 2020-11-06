package com.kagan.control_your_home.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kagan.control_your_home.R
import com.kagan.control_your_home.databinding.FragmentDeviceBinding

class DeviceFragment : Fragment(R.layout.fragment_device) {

    val TAG = "DeviceFragment"
    lateinit var binding: FragmentDeviceBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDeviceBinding.bind(view)
        Log.d(TAG, TAG)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }

        binding.ivBack.setOnClickListener {
            callback.handleOnBackPressed()
        }
    }
}