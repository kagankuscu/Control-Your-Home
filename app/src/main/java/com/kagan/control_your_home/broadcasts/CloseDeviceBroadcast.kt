package com.kagan.control_your_home.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.collection.arrayMapOf
import com.kagan.control_your_home.others.Constant
import com.kagan.control_your_home.others.FunctionConstant
import com.kagan.control_your_home.others.NotificationHelper
import com.kagan.control_your_home.repositries.FirebaseDatabaseRepository

class CloseDeviceBroadcast : BroadcastReceiver() {
    val TAG = "ScheduleTaskHelper"
    private lateinit var firebaseDbRepo: FirebaseDatabaseRepository
    private lateinit var notificationHelper: NotificationHelper

    override fun onReceive(context: Context?, intent: Intent?) {
        firebaseDbRepo = FirebaseDatabaseRepository()
        notificationHelper = NotificationHelper(context)

        Log.d(
            TAG,
            "onReceive: test${
                FunctionConstant.simpleDateFormat(System.currentTimeMillis())
            }"
        )

        notificationHelper.createNotification(
            "Device is close",
            "Time is ${FunctionConstant.simpleDateFormat(System.currentTimeMillis())}"
        )

        intent?.getStringExtra(Constant.LIVING_ROOM)
            ?.let {
                firebaseDbRepo.updateValue(
                    it,
                    arrayMapOf("lamp" to false)
                )
            }
    }
}