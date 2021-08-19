package com.example.weatherappflow.weather.remote.interceptor

import com.example.weatherappflow.utils.FlipperInitializer
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class ResponseInterceptor(flipperInitializer: FlipperInitializer, private val gson: Gson) :
    Interceptor {
    private val httpInterceptor: Interceptor by lazy {
        flipperInitializer.networkInterceptor ?: HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return httpInterceptor.intercept(chain)
    }
}
