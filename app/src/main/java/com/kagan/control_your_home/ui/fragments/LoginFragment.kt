package com.kagan.control_your_home.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.kagan.control_your_home.R
import com.kagan.control_your_home.ui.MainActivity
import com.kagan.control_your_home.ui.SmartHomeViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment(R.layout.fragment_login) {

    val TAG = "LoginFragment"
    lateinit var viewModel: SmartHomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        btnLogin.setOnClickListener {
            Toast.makeText(context, "btnLogin Clicked.", Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.VISIBLE

            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.checkAuth()

                withContext(Dispatchers.Main){
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }

        tvSignUp.setOnClickListener {
            Toast.makeText(context, "btnLogin Clicked.", Toast.LENGTH_SHORT).show()
            view.findNavController().navigate(R.id.action_loginFragment_to_loginRegisterFragment)
        }
    }
}