package com.example.weatherappflow.di.modules

import com.example.weatherappflow.weather.data.repositories.WeatherRepository
import com.example.weatherappflow.weather.data.repositories.WeatherRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<WeatherRepository> {
        WeatherRepositoryImpl(weatherAPI = get())
    }
}
