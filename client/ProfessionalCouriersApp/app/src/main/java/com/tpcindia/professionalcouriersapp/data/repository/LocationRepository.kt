package com.tpcindia.professionalcouriersapp.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.tpcindia.professionalcouriersapp.data.model.CurrentLocation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

class LocationRepository(context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getLocation(): CurrentLocation = suspendCancellableCoroutine { continuation ->
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    continuation.resume(CurrentLocation(latitude, longitude))
                } else {
                    // Request new location data if the last known location is null
                    requestNewLocationData(continuation)
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(continuation: Continuation<CurrentLocation>) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000L).build()

        fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    // Resume the coroutine with the new location data
                    continuation.resume(CurrentLocation(latitude, longitude))
                    // Stop updates after receiving the location
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
        }, null)
    }
}
