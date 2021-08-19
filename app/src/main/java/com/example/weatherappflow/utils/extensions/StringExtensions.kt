package com.example.weatherappflow.utils.extensions

import com.example.weatherappflow.BuildConfig

fun Int.toCelsiusString(): String {
    return "${this}°C"
}

fun String.toImageUrl(): String {
    return "${BuildConfig.IMAGE_URL_SUFIX}$this${BuildConfig.IMAGE_URL_PREFIX}"
}
