package com.kagan.control_your_home.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kagan.control_your_home.R
import com.kagan.control_your_home.ui.MainActivity
import com.kagan.control_your_home.ui.SmartHomeViewModel

class LoginRegisterFragment : Fragment(R.layout.fragment_login_register) {

    val TAG = "LoginFragmentRegister"
    lateinit var viewModel: SmartHomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }
}