package com.kagan.control_your_home.others

import java.text.SimpleDateFormat
import java.util.*

object FunctionConstant {

    fun simpleDateFormat(time: Long): String {
        return SimpleDateFormat("hh:mm a", Locale.UK).format(time)
    }

    fun simpleDateFormatDetails(time: Long): String {
        return SimpleDateFormat("d/MM/y E hh:mm a", Locale.UK).format(time)
    }

    fun formatList(selectedItems: ArrayList<String>): String {
        return Arrays.toString(selectedItems.toArray()).replace("[", "")
            .replace("]", "")
    }
}