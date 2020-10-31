package com.kagan.control_your_home.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.kagan.control_your_home.R
import com.kagan.control_your_home.ui.MainActivity
import com.kagan.control_your_home.ui.SmartHomeViewModel
import kotlinx.android.synthetic.main.fragment_login_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginRegisterFragment : Fragment(R.layout.fragment_login_register) {

    val TAG = "LoginFragmentRegister"
    lateinit var viewModel: SmartHomeViewModel
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        btnRegister.setOnClickListener {
            Log.d(TAG, "Button clicked")

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val passwordRepeat = etPasswordRepeat.text.toString()

            if (password == passwordRepeat) {
                progressBar.visibility = View.VISIBLE

                lifecycleScope.launch(Dispatchers.IO) {
                    createAccount(email, password)

                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.INVISIBLE
                        TODO("Here will add navigation to login page")
                    }
                }
            } else {
                etPasswordRepeat.error = getString(R.string.error_password_same)
            }
        }
    }

    private suspend fun createAccount(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        getString(R.string.user_created_succecfully),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.user_created_failed),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
    }
}