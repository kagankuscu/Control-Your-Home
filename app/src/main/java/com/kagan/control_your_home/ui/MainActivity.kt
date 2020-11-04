package com.kagan.control_your_home.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kagan.control_your_home.R

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: SmartHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(SmartHomeViewModel::class.java)
    }
}