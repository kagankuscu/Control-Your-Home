package com.kagan.control_your_home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kagan.control_your_home.models.Info
import com.kagan.control_your_home.models.Room
import com.kagan.control_your_home.repositries.FirebaseDatabaseRepository

class DBViewModel : ViewModel() {

    private val firebaseDB = FirebaseDatabaseRepository()
    lateinit var info: LiveData<Info>
    lateinit var roomDevice: LiveData<Room>

    fun getInfo() {
        info = firebaseDB.getRealTimeInfo()
    }

    fun getRoom(room: String) {
        roomDevice = firebaseDB.getRoom(room)
    }

    fun updateValue(room: String, map: Map<String, Boolean>) {
        firebaseDB.updateValue(room, map)
    }
}