package com.example.weatherappflow.weather.remote.model

import com.example.weatherappflow.weather.data.domain.HourlyForecast
import com.google.gson.annotations.SerializedName

data class HourlyForecastNetwork(
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lon")
    val lon: Double = 0.0,
    @SerializedName("timezone")
    val timeZone: String = "",
    @SerializedName("timezone_offset")
    val timeZoneOffset: Int = 0,
    @SerializedName("current")
    val current: WeatherCurrentInfo,
    @SerializedName("hourly")
    val hourlyForecast: List<WeatherHourlyInfo>,
)

data class WeatherInfo(
    @SerializedName("dt")
    val dt: Long = 0,
    @SerializedName("temp")
    val temperature: Double = 0.0,
    @SerializedName("feels_like")
    val feelsLike: Double = 0.0,
    @SerializedName("pressure")
    val pressure: Int = 0,
    @SerializedName("humidity")
    val humidity: Int = 0,
    @SerializedName("drew_point")
    val drewPoint: Double = 0.0,
    @SerializedName("uvi")
    val uvi: Double = 0.0,
    @SerializedName("clouds")
    val clouds: Int = 0,
    @SerializedName("visibility")
    val visibility: Long = 0,
    @SerializedName("wind_speed")
    val windSpeed: Double = 0.0,
    @SerializedName("wind_deg")
    val windDegrees: Double = 0.0,
)

data class WeatherNetwork(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("main")
    val main: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("icon")
    val icon: String = "",
)

data class WeatherCurrentInfo(
    val info: WeatherInfo,
    @SerializedName("sunrise")
    val sunrise: Long = 0,
    @SerializedName("sunset")
    val sunset: Long = 0,
    @SerializedName("weather")
    val weather: List<WeatherNetwork>,
)

data class WeatherHourlyInfo(
    @SerializedName("dt")
    val dt: Long = 0,
    @SerializedName("temp")
    val temperature: Double = 0.0,
    @SerializedName("feels_like")
    val feelsLike: Double = 0.0,
    @SerializedName("pressure")
    val pressure: Int = 0,
    @SerializedName("humidity")
    val humidity: Int = 0,
    @SerializedName("drew_point")
    val drewPoint: Double = 0.0,
    @SerializedName("uvi")
    val uvi: Double = 0.0,
    @SerializedName("clouds")
    val clouds: Int = 0,
    @SerializedName("visibility")
    val visibility: Long = 0,
    @SerializedName("wind_speed")
    val windSpeed: Double = 0.0,
    @SerializedName("wind_deg")
    val windDegrees: Double = 0.0,
    @SerializedName("wind_gust")
    val windGust: Double = 0.0,
    @SerializedName("weather")
    val weather: List<WeatherNetwork>,
    @SerializedName("pop")
    val pop: Double = 0.0,
)

fun WeatherHourlyInfo.mapToDomain(): HourlyForecast {
    return HourlyForecast(
        this.dt,
        this.temperature.toInt(),
        this.weather[0].icon
    )
}
