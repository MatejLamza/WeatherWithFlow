package com.example.weatherappflow.common.state

sealed class State

/**
 * Indicates that there is an action running, such as content fetching
 */
object Loading : State()

/**
 * Indicates that the action has resulted in success
 */
data class Done(val hasData: Boolean? = null) : State()

/**
 * Indicates that the action has failed and contains error message resource ID and optional
 * exception
 */
data class Error(val throwable: Throwable? = null) : State()
