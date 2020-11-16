package com.kagan.control_your_home.others

import java.text.SimpleDateFormat
import java.util.*

object FunctionConstant {

    fun simpleDateFormat(time: Long): String {
        return SimpleDateFormat("hh:mm a", Locale.UK).format(time)
    }
}