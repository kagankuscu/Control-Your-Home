package com.kagan.control_your_home.models

data class Room(
    val fan: Boolean,
    val lamp: Boolean,
    val motionSensor: Boolean,
    val tv: Boolean
) {
}