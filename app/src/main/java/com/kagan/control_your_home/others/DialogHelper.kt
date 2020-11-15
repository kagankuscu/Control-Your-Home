package com.kagan.control_your_home.others

import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.util.Log
import com.kagan.control_your_home.R

class DialogHelper(base: Context?) : ContextWrapper(base) {
    val TAG = "DeviceFragment"

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
        val selectedItems = ArrayList<String>()
        val checkDays = BooleanArray(7)

        return AlertDialog.Builder(baseContext)
            .setTitle("Days")
            .setMultiChoiceItems(
                R.array.days,
                checkDays,
                DialogInterface.OnMultiChoiceClickListener { _, which, isChecked ->
                    if (isChecked) {
                        selectedItems.add(resources.getStringArray(R.array.days)[which])
                        checkDays[which] = true
                    } else {
                        selectedItems.remove(resources.getStringArray(R.array.days)[which])
                        checkDays[which] = false
                    }
                })
            .setPositiveButton("OK", DialogInterface.OnClickListener { _, which ->
                Log.d(TAG, "positive: day,$which")
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
}