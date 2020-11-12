package com.kagan.control_your_home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kagan.control_your_home.models.Info
import com.kagan.control_your_home.repositries.FirebaseDatabaseRepository

class DBViewModel : ViewModel() {

    private val firebaseDB = FirebaseDatabaseRepository()
    lateinit var info: LiveData<Info>

    fun getInfo() {
        info = firebaseDB.getRealTimeInfo()
    }
}