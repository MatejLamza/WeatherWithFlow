package com.example.weatherappflow.weather.viewmodels

import android.location.Location
import androidx.lifecycle.*
import com.example.weatherappflow.common.state.State
import com.example.weatherappflow.common.state.StateLiveData
import com.example.weatherappflow.common.state.launch
import com.example.weatherappflow.common.state.launchWithState
import com.example.weatherappflow.utils.helpers.LocationHelper
import com.example.weatherappflow.utils.listeners.NetworkStatusListener
import com.example.weatherappflow.weather.data.domain.City
import com.example.weatherappflow.weather.data.domain.HourlyForecast
import com.example.weatherappflow.weather.data.repositories.WeatherRepository
import com.example.weatherappflow.weather.remote.model.mapToDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class WeatherViewModel(
    private val weatherRepo: WeatherRepository,
    networkStatusListener: NetworkStatusListener,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _state = StateLiveData()
    val state: LiveData<State> = _state

    private val _currentWeather = MutableLiveData<City>()
    val currentWeather: LiveData<City> = _currentWeather

    val networkStatus = networkStatusListener.networkStatus
        .asLiveData(Dispatchers.IO)

    val location = MutableLiveData<Location>()

    private val _hourlyForecast = MutableLiveData<List<HourlyForecast>>()
    val hourlyForecast: LiveData<List<HourlyForecast>> = _hourlyForecast

    init {
        refreshLocation()
    }

    fun getCurrentWeather(city: String) {
        launchWithState(_state) {
            weatherRepo.fetchCurrentWeather(city).collect { cityNetwork ->
                _currentWeather.value = cityNetwork
                val newLocation = Location("")
                newLocation.longitude = cityNetwork.cordinates.longitude
                newLocation.latitude = cityNetwork.cordinates.latitude
                location.value = newLocation
            }
        }
    }

    fun refreshLocation() {
        launchWithState(_state) {
            locationHelper.getLocation().catch { e -> _state.postError(e) }.collect {
                location.value = it
            }
        }
    }

    val updateCurrentLocation = object : MediatorLiveData<Boolean>() {
        init {
            addSource(location) {
                process(loc = it)
            }
        }

        private fun process(loc: Location? = location.value) {
            if (loc != null) {
                launchWithState(_state) {
                    weatherRepo.fetchCurrentWeatherForCordinates(loc.longitude, loc.longitude)
                        .collect {
                            _currentWeather.value = it
                        }
                }
                launch {
                    weatherRepo.fetchHourlyForecast(loc.longitude, loc.latitude).collect {
                        _hourlyForecast.value = it.hourlyForecast.take(8).map {
                            it.mapToDomain()
                        }
                    }
                }
            }
        }
    }
}
