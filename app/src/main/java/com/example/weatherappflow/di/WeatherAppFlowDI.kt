package com.example.weatherappflow.di

import android.app.Application
import com.example.weatherappflow.di.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class WeatherAppFlowDI(private val application: Application) {

    private lateinit var koinApplication: KoinApplication
    private val modules: List<Module> = listOf(
        networkModule,
        repositoryModule,
        viewModelModule,
        appModule,
        dialogModule
    )

    fun initialize() {
        koinApplication = startKoin {
            androidContext(application)
            modules(modules)
        }
    }
}
