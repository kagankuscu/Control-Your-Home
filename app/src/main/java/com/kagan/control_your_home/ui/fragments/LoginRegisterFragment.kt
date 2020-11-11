package com.kagan.control_your_home.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.kagan.control_your_home.R
import com.kagan.control_your_home.databinding.FragmentLoginRegisterBinding
import com.kagan.control_your_home.ui.MainActivity
import com.kagan.control_your_home.ui.viewmodel.SmartHomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginRegisterFragment : Fragment(R.layout.fragment_login_register) {

    val TAG = "LoginFragmentRegister"
    lateinit var viewModel: SmartHomeViewModel
    lateinit var mAuth: FirebaseAuth
    lateinit var binding: FragmentLoginRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginRegisterBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        binding.btnRegister.setOnClickListener {
            Log.d(TAG, "Button clicked")

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordRepeat = binding.etPasswordRepeat.text.toString()

            if (password == passwordRepeat) {
                binding.progressBar.visibility = View.VISIBLE

                lifecycleScope.launch(Dispatchers.IO) {
                    createAccount(email, password)
                }
            } else {
                binding.etPasswordRepeat.error = getString(R.string.error_password_same)
            }
        }
    }

    private suspend fun createAccount(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        getString(R.string.user_created_successfully),
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