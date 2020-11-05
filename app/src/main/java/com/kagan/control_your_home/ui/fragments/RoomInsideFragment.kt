package com.kagan.control_your_home.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.kagan.control_your_home.R

class RoomInsideFragment : Fragment(R.layout.fragment_login_register) {

    val TAG = "RoomInsideFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, TAG)
    }
}