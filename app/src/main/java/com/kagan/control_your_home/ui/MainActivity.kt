package com.kagan.control_your_home.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kagan.control_your_home.R
import com.kagan.control_your_home.ui.viewmodel.SmartHomeViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: SmartHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(SmartHomeViewModel::class.java)

        Log.d("Main", "main on created")
    }
}