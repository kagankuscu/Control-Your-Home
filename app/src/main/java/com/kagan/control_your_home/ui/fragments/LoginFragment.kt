package com.kagan.control_your_home.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
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
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        mAuth = FirebaseAuth.getInstance()

        val currentUser = mAuth.currentUser;
        Log.d(TAG, "current User :${currentUser?.displayName}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener {
            Toast.makeText(context, "btnLogin Clicked.", Toast.LENGTH_SHORT).show()

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty()) {
                etEmail.error = getString(R.string.error_empty)
                return@setOnClickListener
            } else if (password.isEmpty()) {
                etPassword.error = getString(R.string.error_empty)
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE

            lifecycleScope.launch(Dispatchers.IO) {
                checkAuth(email, password)
            }
        }

        tvSignUp.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_loginFragment_to_loginRegisterFragment)
        }
    }

    private suspend fun checkAuth(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    view.let {
                        findNavController().navigate(R.id.action_loginFragment_to_roomFragment)
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
    }
}