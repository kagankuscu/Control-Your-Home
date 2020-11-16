package com.kagan.control_your_home.ui.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.kagan.control_your_home.others.Constant.FROM
import com.kagan.control_your_home.others.Constant.TO
import com.kagan.control_your_home.viewmodel.TimeViewModel
import java.util.*

class TimePickerFragment : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {

    val TAG = "TimePickerFragment"
    private val args: TimePickerFragmentArgs by navArgs()
    private lateinit var timeViewModel: TimeViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d(TAG, "onTimeSet: $hourOfDay:$minute")
        timeViewModel = ViewModelProvider(requireActivity()).get(TimeViewModel::class.java)
        val c = Calendar.getInstance()
        c.set(
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH),
            hourOfDay,
            minute,
            0
        )

        when (args.time) {
            FROM -> {
                timeViewModel.startTime.value = c.timeInMillis
            }
            TO -> {
                timeViewModel.endTime.value = c.timeInMillis
            }
        }
    }
}