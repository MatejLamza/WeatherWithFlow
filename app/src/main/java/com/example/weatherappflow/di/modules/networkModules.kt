package com.example.weatherappflow.di.modules

import com.example.weatherappflow.BuildConfig
import com.example.weatherappflow.di.Qualifiers
import com.example.weatherappflow.utils.listeners.NetworkStatusListener
import com.example.weatherappflow.weather.remote.WeatherAPI
import com.example.weatherappflow.weather.remote.interceptor.AuthInterceptor
import com.example.weatherappflow.weather.remote.interceptor.ResponseInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule: Module = module {

    single {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single<GsonConverterFactory> {
        GsonConverterFactory.create(get<Gson>())
    }

    single { AuthInterceptor() }
    single(Qualifiers.apiFullUrl) { BuildConfig.API_URL }
    single { ResponseInterceptor(flipperInitializer = get(), gson = get()) }



    single {
        OkHttpClient()
            .newBuilder()
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(get<AuthInterceptor>())
            .addNetworkInterceptor(get<ResponseInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(get<String>(Qualifiers.apiFullUrl))
            .addConverterFactory(get<GsonConverterFactory>())
            .client(get())
            .build()
    }
    single { get<Retrofit>().create(WeatherAPI::class.java) }
    single { NetworkStatusListener(context = get()) }
}
