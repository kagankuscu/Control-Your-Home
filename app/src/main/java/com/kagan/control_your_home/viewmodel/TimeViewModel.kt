package com.kagan.control_your_home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimeViewModel : ViewModel() {

    val startTime = MutableLiveData<Long>()

    val endTime = MutableLiveData<Long>()

}