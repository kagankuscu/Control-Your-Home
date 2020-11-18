package com.kagan.control_your_home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimeViewModel : ViewModel() {

    val startTime = MutableLiveData<ArrayList<Int>>()

    val endTime = MutableLiveData<ArrayList<Int>>()

    val selectedDays = MutableLiveData<ArrayList<String>>()

    val checkDays = MutableLiveData<BooleanArray>()
}