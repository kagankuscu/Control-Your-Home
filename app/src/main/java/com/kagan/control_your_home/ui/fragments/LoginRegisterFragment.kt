package com.kagan.control_your_home.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kagan.control_your_home.R
import com.kagan.control_your_home.databinding.FragmentLoginRegisterBinding
import com.kagan.control_your_home.ui.MainActivity
import com.kagan.control_your_home.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginRegisterFragment : Fragment(R.layout.fragment_login_register) {

    val TAG = "LoginFragmentRegister"
    lateinit var viewModel: LoginViewModel
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

            hideKeyboard()

            if (password == passwordRepeat) {
                binding.progressBar.visibility = View.VISIBLE

                viewModel.createNewAccount(email, password)
                viewModel.newUser.observe(viewLifecycleOwner, Observer {
                    Log.d(TAG, "onViewCreated: newUser $it")
                    binding.progressBar.visibility = View.INVISIBLE
                    findNavController().navigate(R.id.action_loginRegisterFragment_to_roomFragment)
                })

            } else {
                binding.etPasswordRepeat.error = getString(R.string.error_password_same)
            }
        }
    }

    private fun hideKeyboard() {
        val currentFocus = (activity as MainActivity).currentFocus
        val imm =
            this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}