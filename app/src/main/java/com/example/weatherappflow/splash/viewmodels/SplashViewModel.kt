package com.example.weatherappflow.splash.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherappflow.common.state.State
import com.example.weatherappflow.common.state.StateLiveData
import com.example.weatherappflow.common.state.asLiveDataWithState
import com.example.weatherappflow.utils.listeners.NetworkStatusListener

class SplashViewModel(
    private val networkStatusListener: NetworkStatusListener
) : ViewModel() {
    private val _state = StateLiveData()
    val state: LiveData<State> = _state

    val networkStatus = networkStatusListener.networkStatus.asLiveDataWithState(_state)
}
