package com.example.weatherappflow.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.weatherappflow.common.state.launch
import com.example.weatherappflow.splash.views.SplashActivity
import com.example.weatherappflow.weather.views.IS_CONNECTED_TO_INTERNET

class NavigationViewModel : ViewModel() {
    fun navigateToApp(
        source: SplashActivity,
        destination: AppCompatActivity,
        isDeviceConnectedToInternet: Boolean
    ) {
        launch {
            source.startActivity(
                Intent(source, destination::class.java).putExtra(
                    IS_CONNECTED_TO_INTERNET,
                    isDeviceConnectedToInternet
                )
            )
            source.finishAffinity()
        }
    }
}
