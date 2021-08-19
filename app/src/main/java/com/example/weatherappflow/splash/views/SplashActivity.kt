package com.example.weatherappflow.splash.views

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.weatherappflow.R
import com.example.weatherappflow.common.mvvm.BaseActivity
import com.example.weatherappflow.common.state.NetworkStatus
import com.example.weatherappflow.common.state.observe
import com.example.weatherappflow.splash.viewmodels.SplashViewModel
import com.example.weatherappflow.weather.views.WeatherActivity
import com.github.florent37.runtimepermission.kotlin.coroutines.experimental.askPermission
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity() {
    private val splashViewModel: SplashViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        bind()
        lifecycleScope.launchWhenCreated {
            kotlin.runCatching {
                askPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }.onFailure {
                Toast.makeText(
                    baseContext,
                    getString(R.string.permission_reason),
                    Toast.LENGTH_LONG
                ).show()
            }
            navigation.navigateToApp(
                this@SplashActivity,
                WeatherActivity(),
                splashViewModel.networkStatus.value == NetworkStatus.Connected
            )
        }
    }

    private fun bind() {
        splashViewModel.networkStatus.observe(this) {}
        splashViewModel.state.observe(this, this) {}
    }
}
