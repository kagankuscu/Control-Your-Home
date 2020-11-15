package com.kagan.control_your_home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimeViewModel : ViewModel() {

    val startTime = MutableLiveData<List<Int>>()

    val endTime = MutableLiveData<List<Int>>()

    init {
        startTime.value = arrayListOf(0, 0)
        endTime.value = arrayListOf(0, 0)
    }
}