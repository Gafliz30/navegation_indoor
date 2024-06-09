package com.example.pucmm_map

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class RealTimeLocationHandler(private val activity: Activity) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback


    init {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000 // Intervalo en milisegundos para actualizar la ubicación (10 segundos)
            fastestInterval = 5000 // El intervalo más rápido en milisegundos para recibir actualizaciones (5 segundos)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.forEach { location ->
                    logLocation(location)
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Si los permisos no están concedidos, solicitarlos
            Toast.makeText(activity, "Permiso de ubicación no concedido", Toast.LENGTH_SHORT).show()
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    fun stopLocationUpdates() {
        if (::locationCallback.isInitialized) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    private fun logLocation(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude
        Log.d("RealTimeLocationHandler", "Ubicación en tiempo real - Latitud: $latitude, Longitud: $longitude")
    }
}