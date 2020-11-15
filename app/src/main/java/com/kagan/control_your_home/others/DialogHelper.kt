package com.kagan.control_your_home.others

import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.util.Log
import com.kagan.control_your_home.R
import java.util.*
import kotlin.collections.ArrayList

class DialogHelper(base: Context?) : ContextWrapper(base) {
    val TAG = "DeviceFragment"
    private var selectedItems = ArrayList<String>()

    fun createTimeDialog(): AlertDialog.Builder? {
        return AlertDialog.Builder(baseContext)
            .setTitle("Time")
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                Log.d(TAG, "createDialog: $dialog $id")
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                Log.d(TAG, "createDialog: $dialog $id")
            })
    }

    fun createDayDialog(): AlertDialog.Builder? {
        val checkDays = BooleanArray(7)

        return AlertDialog.Builder(baseContext)
            .setTitle("Days")
            .setMultiChoiceItems(
                R.array.days,
                checkDays,
                DialogInterface.OnMultiChoiceClickListener { _, which, isChecked ->
                    if (isChecked) {
                        selectedItems.add(resources.getStringArray(R.array.days_short)[which])
                        checkDays[which] = true
                    } else {
                        selectedItems.remove(resources.getStringArray(R.array.days_short)[which])
                        checkDays[which] = false
                    }
                })
            .setNeutralButton("Reset", DialogInterface.OnClickListener { _, _ ->
                Log.d(TAG, "neutral")
                selectedItems.clear()
                for (i in checkDays.indices) {
                    checkDays[i] = false
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
                Log.d(TAG, "negative: ")
            })
    }

    fun getDays(): String {
        selectedItems.sort()
        var list = ""

        list = if (selectedItems.size == 7) {
            "Everyday"
        } else {
            Arrays.toString(selectedItems.toArray()).replace("[", "")
                .replace("]", "");
        }
        return list
    }
}