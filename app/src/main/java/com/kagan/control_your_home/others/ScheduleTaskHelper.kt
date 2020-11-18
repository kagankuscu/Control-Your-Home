package com.kagan.control_your_home.others

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.kagan.control_your_home.broadcasts.CloseDeviceBroadcast
import com.kagan.control_your_home.broadcasts.OpenDeviceBroadcast
import com.kagan.control_your_home.others.Constant.LIVING_ROOM
import java.util.*

class ScheduleTaskHelper(base: Context?) : ContextWrapper(base) {

    val TAG = "ScheduleTaskHelper"

    private fun getAlarmManager(): AlarmManager {
        return getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private fun getPendingIntent(isOpen: Boolean): PendingIntent? {
        val broadcast = when (isOpen) {
            true -> OpenDeviceBroadcast::class.java
            false -> CloseDeviceBroadcast::class.java
        }

        val i = Intent(baseContext, broadcast)
        i.putExtra(LIVING_ROOM, LIVING_ROOM)

        return PendingIntent.getBroadcast(baseContext, 0, i, 0)
    }

    private fun setCalendar(hourOfDay: Int, minute: Int, dayOfWeek: Int): Long {
        val c = Calendar.getInstance()

        c.set(Calendar.DAY_OF_WEEK, dayOfWeek)
        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
        c.set(Calendar.MINUTE, minute)

        return c.timeInMillis
    }

    fun setAlarm(hourOfDay: Int, minute: Int, dayOfWeek: Int, broadcast: Boolean) {

        val time = setCalendar(hourOfDay, minute, dayOfWeek)

        val pendingIntent = getPendingIntent(broadcast)

        Log.d(
            TAG,
            "setAlarm: waiting ${
                FunctionConstant.simpleDateFormatDetails(time)
            }"
        )

        try {
            getAlarmManager()
                .setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    AlarmManager.INTERVAL_DAY * 7,
                    pendingIntent
                )
        } catch (e: Exception) {
            Log.d(TAG, "onTimeSet: ${e.message}")
        }
    }

    fun cancelAlarmAll() {
        val pendingIntentBroadcastOpen = getPendingIntent(true)
        val pendingIntentBroadcastClose = getPendingIntent(false)
        getAlarmManager().cancel(pendingIntentBroadcastOpen)
        getAlarmManager().cancel(pendingIntentBroadcastClose)
    }

    fun cancelAlarmOpen() {
        val pendingIntentBroadcastOpen = getPendingIntent(true)
        getAlarmManager().cancel(pendingIntentBroadcastOpen)
    }

    fun cancelAlarmClose() {
        val pendingIntentBroadcastClose = getPendingIntent(false)
        getAlarmManager().cancel(pendingIntentBroadcastClose)
    }
}