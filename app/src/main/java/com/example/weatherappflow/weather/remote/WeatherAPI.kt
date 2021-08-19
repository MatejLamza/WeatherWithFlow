package com.example.weatherappflow.weather.remote

import com.example.weatherappflow.weather.remote.model.CityNetwork
import com.example.weatherappflow.weather.remote.model.HourlyForecastNetwork
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("weather")
    suspend fun fetchCurrentWeather(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric"
    ): CityNetwork

    @GET("weather")
    suspend fun fetchCurrentWeatherForCordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric"
    ): CityNetwork

    @GET("onecall")
    suspend fun fetchHourlyForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String = "alerts,minutely,daily",
        @Query("units") units: String = "metric"
    ): HourlyForecastNetwork
}
