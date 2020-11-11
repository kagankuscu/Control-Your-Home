package com.kagan.control_your_home.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kagan.control_your_home.R
import com.kagan.control_your_home.databinding.FragmentLoginBinding
import com.kagan.control_your_home.ui.MainActivity
import com.kagan.control_your_home.ui.SmartHomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    val TAG = "LoginFragment"
    lateinit var viewModel: SmartHomeViewModel
    lateinit var mAuth: FirebaseAuth
    lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        val currentUser = mAuth.currentUser;
        Log.d(TAG, "current User :${currentUser?.displayName}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        // When i turn the phone app suddenly close i solved the problem
        // changing viewModel initialized from onCreate to onViewCreated
        viewModel = (activity as MainActivity).viewModel


        binding.btnLogin.setOnClickListener {
            Toast.makeText(context, "btnLogin Clicked.", Toast.LENGTH_SHORT).show()

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty()) {
                binding.etEmail.error = getString(R.string.error_empty)
                return@setOnClickListener
            } else if (password.isEmpty()) {
                binding.etPassword.error = getString(R.string.error_empty)
                return@setOnClickListener
            }

            hideKeyboard()

            binding.progressBar.visibility = View.VISIBLE

            lifecycleScope.launch(Dispatchers.IO) {
                checkAuth(email, password)
            }
        }

        binding.tvSignUp.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_loginFragment_to_loginRegisterFragment)
        }
    }

    private fun hideKeyboard() {
        val currentFocus = (activity as MainActivity).currentFocus
        val imm =
            this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
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