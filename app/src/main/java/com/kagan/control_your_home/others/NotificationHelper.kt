package com.kagan.control_your_home.others

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import androidx.core.app.NotificationCompat
import com.kagan.control_your_home.R
import com.kagan.control_your_home.others.Constant.NOTIFICATION_ID
import com.kagan.control_your_home.others.Constant.NOTIFICATION_NAME

class NotificationHelper(base: Context?) : ContextWrapper(base) {

    private var nManager: NotificationManager? = null

    init {
        createChannel()
    }

    private fun getNotificationManager(): NotificationManager {
        if (nManager == null) {
            nManager =
                baseContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return nManager as NotificationManager
    }

    private fun createChannel() {
        val channel =
            NotificationChannel(
                NOTIFICATION_ID,
                NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

        getNotificationManager().createNotificationChannel(channel)
    }

    fun createNotification(contentTitle: String, contentText: String) {
        val not = NotificationCompat.Builder(baseContext, NOTIFICATION_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        getNotificationManager().notify(1, not)
    }
}