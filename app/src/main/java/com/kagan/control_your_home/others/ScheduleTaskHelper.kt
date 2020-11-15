package com.kagan.control_your_home.others

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import com.kagan.control_your_home.broadcasts.OpenDeviceBroadcast
import com.kagan.control_your_home.others.Constant.LIVING_ROOM
import java.lang.Exception
import java.text.SimpleDateFormat

class ScheduleTaskHelper(base: Context?) : ContextWrapper(base) {

    val TAG = "ScheduleTaskHelper"

    private fun getAlarmManager(): AlarmManager {
        return getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun setAlarm(inTimeMillis: Long) {
        val i = Intent(baseContext, OpenDeviceBroadcast::class.java)
        i.putExtra(LIVING_ROOM, LIVING_ROOM)
        val pendingIntent = PendingIntent.getBroadcast(baseContext, 0, i, 0)

        Log.d(
            TAG,
            "setAlarm: waiting ${
                SimpleDateFormat.getDateTimeInstance()
                    .format(inTimeMillis)
            }"
        )

        try {
            getAlarmManager()
                .set(
                    AlarmManager.RTC_WAKEUP,
                    inTimeMillis,
                    pendingIntent
                )
        } catch (e: Exception) {
            Log.d(TAG, "onTimeSet: ${e.message}")
        }
    }
}