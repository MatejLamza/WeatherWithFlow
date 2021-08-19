package com.example.weatherappflow.utils.helpers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.example.weatherappflow.utils.PermissionException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

class LocationHelper(private val context: Context) {

    private val locationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val locationChannel = ConflatedBroadcastChannel<Location>()

    private suspend fun setLocation() {
        withContext(IO) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                locationClient.lastLocation.addOnSuccessListener {
                    if (it != null) {
                        locationChannel.offer(it)
                    }
                }
            } else {
                locationChannel.offer(throw PermissionException())
            }
        }
    }

    fun getLocation(): Flow<Location> {
        return locationChannel.asFlow()
            .onStart { setLocation() }
            .distinctUntilChanged()
    }
}
