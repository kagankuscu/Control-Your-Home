package com.kagan.control_your_home.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import androidx.collection.arrayMapOf
import com.kagan.control_your_home.R
import com.kagan.control_your_home.others.Constant.LIVING_ROOM
import com.kagan.control_your_home.repositries.FirebaseDatabaseRepository
import java.text.SimpleDateFormat

class OpenDeviceBroadcast : BroadcastReceiver() {
    val TAG = "ScheduleTaskHelper"
    private lateinit var firebaseDbRepo: FirebaseDatabaseRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        firebaseDbRepo = FirebaseDatabaseRepository()

        Log.d(
            TAG,
            "onReceive: test${
                SimpleDateFormat.getDateTimeInstance().format(System.currentTimeMillis())
            }"
        )
        val m = MediaPlayer.create(context, R.raw.song)
        m.start()

        intent?.getStringExtra(LIVING_ROOM)
            ?.let { firebaseDbRepo.updateValue(it, arrayMapOf("lamp" to true)) }
    }
}