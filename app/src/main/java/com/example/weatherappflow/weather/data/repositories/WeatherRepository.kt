package com.example.weatherappflow.weather.data.repositories

import com.example.weatherappflow.weather.data.domain.City
import com.example.weatherappflow.weather.remote.model.HourlyForecastNetwork
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun fetchCurrentWeather(city: String, units: String = "metric"): Flow<City>
    suspend fun fetchCurrentWeatherForCordinates(
        longitude: Double,
        latitude: Double,
        units: String = "metric"
    ): Flow<City>

    suspend fun fetchHourlyForecast(
        longitude: Double,
        latitude: Double,
        exclude: String = "alerts, minutely, daily",
        units: String = "metric"
    ): Flow<HourlyForecastNetwork>
}
