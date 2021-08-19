package com.example.weatherappflow.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.toHoursString(): String {
    val sfd = SimpleDateFormat("HH:mm")
    val date = Date(this * 1000)
    return sfd.format(date).toString()
}
