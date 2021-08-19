package com.example.weatherappflow.di.modules

import com.example.weatherappflow.utils.FlipperInitializer
import com.example.weatherappflow.utils.helpers.ErrorParser
import com.example.weatherappflow.utils.helpers.LocationHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single { FlipperInitializer(androidApplication()) }
    single { LocationHelper(context = get()) }
    single { ErrorParser(gson = get()) }

}
