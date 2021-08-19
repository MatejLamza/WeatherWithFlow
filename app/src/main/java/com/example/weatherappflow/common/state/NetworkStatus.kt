package com.example.weatherappflow.common.state

sealed class NetworkStatus {
    object Connected : NetworkStatus()
    object NotConnected : NetworkStatus()
}
