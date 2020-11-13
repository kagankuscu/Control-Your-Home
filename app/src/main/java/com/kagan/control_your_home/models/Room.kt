package com.kagan.control_your_home.models

data class Room(
    val fan: Boolean = false,
    val lamp: Boolean = false,
    val motionSensor: Boolean = false,
    val tv: Boolean = false
)