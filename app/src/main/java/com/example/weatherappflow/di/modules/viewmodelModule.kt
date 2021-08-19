package com.example.weatherappflow.di.modules

import com.example.weatherappflow.navigation.NavigationViewModel
import com.example.weatherappflow.splash.viewmodels.SplashViewModel
import com.example.weatherappflow.weather.viewmodels.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel {
        WeatherViewModel(
            weatherRepo = get(),
            networkStatusListener = get(),
            locationHelper = get()
        )
    }
    viewModel { NavigationViewModel() }
    viewModel { SplashViewModel(networkStatusListener = get()) }
}
