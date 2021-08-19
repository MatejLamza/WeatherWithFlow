package com.example.weatherappflow.utils

class PermissionException : Exception() {
    override val message: String
        get() = "No permission granted"
}
