package com.example.weatherappflow.weather.data.repositories

import com.example.weatherappflow.weather.data.domain.City
import com.example.weatherappflow.weather.remote.WeatherAPI
import com.example.weatherappflow.weather.remote.model.HourlyForecastNetwork
import com.example.weatherappflow.weather.remote.model.mapToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepositoryImpl(private val weatherAPI: WeatherAPI) : WeatherRepository {
    override suspend fun fetchCurrentWeather(city: String, units: String) = flow<City> {
        emit(weatherAPI.fetchCurrentWeather(city, units).mapToDomain())
    }

    override suspend fun fetchCurrentWeatherForCordinates(
        longitude: Double,
        latitude: Double,
        units: String
    ): Flow<City> = flow {
        emit(weatherAPI.fetchCurrentWeatherForCordinates(latitude, longitude, units).mapToDomain())
    }

    override suspend fun fetchHourlyForecast(
        longitude: Double,
        latitude: Double,
        exclude: String,
        units: String
    ): Flow<HourlyForecastNetwork> = flow {
        emit(weatherAPI.fetchHourlyForecast(latitude, longitude, exclude, units))
    }
}
