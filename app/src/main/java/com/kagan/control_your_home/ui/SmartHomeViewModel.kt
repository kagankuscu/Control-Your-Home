package com.kagan.control_your_home.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class SmartHomeViewModel: ViewModel() {

    val TAG = "SmartHomeViewModel"

    suspend fun checkAuth() {
        delay(3000L)
        Log.d(TAG, "Checking Authentication.")
    }
}