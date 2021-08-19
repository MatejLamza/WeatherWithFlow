package com.example.weatherappflow.common.mvvm

interface View {
    fun showLoading()
    fun dismissLoading()
    fun showError(error: Throwable)
}
