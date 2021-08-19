package com.example.weatherappflow

import android.app.Application
import com.example.weatherappflow.di.WeatherAppFlowDI
import com.example.weatherappflow.utils.FlipperInitializer
import org.koin.android.ext.android.get

class WeatherFlowApplication : Application() {
    private val weatherAppDI: WeatherAppFlowDI by lazy { WeatherAppFlowDI(this) }
    override fun onCreate() {
        super.onCreate()
        weatherAppDI.initialize()
        get<FlipperInitializer>().initialize()
    }
}
