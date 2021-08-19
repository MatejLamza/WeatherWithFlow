package com.example.weatherappflow.weather.views

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.weatherappflow.R
import com.example.weatherappflow.common.mvvm.BaseActivity
import com.example.weatherappflow.common.state.NetworkStatus
import com.example.weatherappflow.common.state.observe
import com.example.weatherappflow.utils.PermissionException
import com.example.weatherappflow.utils.extensions.toCelsiusString
import com.example.weatherappflow.weather.adapters.HourlyForecastAdapter
import com.example.weatherappflow.weather.viewmodels.WeatherViewModel
import com.github.florent37.runtimepermission.kotlin.coroutines.experimental.askPermission
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException


const val IS_CONNECTED_TO_INTERNET = "is-device-connected-to-internet"

class WeatherActivity : BaseActivity() {

    private val hourlyForecastAdapter: HourlyForecastAdapter by lazy {
        HourlyForecastAdapter()
    }

    private val isDeviceConnectedToInternet: Boolean by lazy {
        intent.getBooleanExtra(IS_CONNECTED_TO_INTERNET, false)
    }

    private val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        adjustContentVisiblity(isDeviceConnectedToInternet)
        setupUI()
        bind()
    }

    private fun setupUI() {
        initRecyclerView()
        searchLocation.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    weatherViewModel.getCurrentWeather(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        myLocation.setOnClickListener {
            val locationManager =
                this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isLocationEnabled = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                locationManager.isLocationEnabled
            } else {
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            }
            if (isLocationEnabled) {
                weatherViewModel.refreshLocation()
            } else {
                Toast.makeText(this, getString(R.string.turn_on_your_location), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun bind() {
        weatherViewModel.updateCurrentLocation.observe(this) {}
        weatherViewModel.currentWeather.observe(this) { city ->
            locationName.text = city.locationName
            temperature.text = city.temperature.temperature.toCelsiusString()
            description.text = city.weather.description
            maxMinTemperature.text =
                "${city.temperature.temperatureMax.toCelsiusString()} / ${city.temperature.temperatureMin.toCelsiusString()}"
            feelsLike.text =
                getString(
                    R.string.temperature_feels_like,
                    city.temperature.feelsLike.toCelsiusString()
                )
            Glide.with(this)
                .load("https://openweathermap.org/img/wn/${city.weather.icon}@2x.png")
                .into(weatherIcon)
        }
        weatherViewModel.state.observe(this, this, onError = {
            when (it) {
                is PermissionException -> {
                    lifecycleScope.launchWhenCreated {
                        kotlin.runCatching {
                            askPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }
                }
                is HttpException -> {
                    showError(it)
                }
                else -> {
                    Toast.makeText(
                        this,
                        getString(R.string.turn_on_internet),
                        Toast.LENGTH_LONG
                    ).show()
                    adjustContentVisiblity(false)
                }
            }
        }) {}
        weatherViewModel.hourlyForecast.observe(this) {
            hourlyForecastAdapter.hourlyForecast = it
        }
        weatherViewModel.networkStatus.distinctUntilChanged().observe(this) { status ->
            when (status) {
                is NetworkStatus.NotConnected -> {
                    adjustContentVisiblity(false)
                    Toast.makeText(
                        this,
                        getString(R.string.no_internet_connection),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is NetworkStatus.Connected -> {
                    adjustContentVisiblity(true)
                }
            }
        }
    }

    private fun initRecyclerView() {
        hourlyForecastList.adapter = hourlyForecastAdapter
    }

    private fun adjustContentVisiblity(isUserOnline: Boolean) {
        if (isUserOnline) {
            weatherContainer.visibility = View.VISIBLE
            hourlyContainer.visibility = View.VISIBLE
            myLocation.visibility = View.VISIBLE
            noInternetConnectionTitle.visibility = View.GONE
            noInternetConnectionMessage.visibility = View.GONE
        } else {
            weatherContainer.visibility = View.GONE
            hourlyContainer.visibility = View.GONE
            myLocation.visibility = View.GONE
            noInternetConnectionTitle.visibility = View.VISIBLE
            noInternetConnectionMessage.visibility = View.VISIBLE
        }
    }
}
