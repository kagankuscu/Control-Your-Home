package com.kagan.control_your_home.others

import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import java.util.*
import kotlin.collections.ArrayList

class DialogHelper(base: Context?) : ContextWrapper(base) {
    val TAG = "DeviceFragment"

    fun createDayDialog(): AlertDialog.Builder? {
        return AlertDialog.Builder(baseContext)
            .setTitle("Days")
    }
}