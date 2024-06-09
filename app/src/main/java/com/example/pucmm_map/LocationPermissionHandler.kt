package com.example.pucmm_map

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class LocationPermissionHandler(private val activity: Activity) {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    fun checkAndRequestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // Si los permisos no están concedidos, solicitarlos
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Si los permisos ya están concedidos, obtener la ubicación
            onLocationPermissionGranted()
        }
    }

    fun handlePermissionsResult(requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permiso concedido, obtener la ubicación
                    onLocationPermissionGranted()
                } else {
                    // Permiso denegado, mostrar mensaje al usuario
                    Toast.makeText(activity, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onLocationPermissionGranted() {
        // Aquí puedes obtener la ubicación del usuario
        Toast.makeText(activity, "Permiso de ubicación concedido", Toast.LENGTH_SHORT).show()
    }

    fun checkLocationEnabled() {
        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // La localización está apagada, mostrar diálogo para activarla
            showLocationAlertDialog()
        }
    }

    private fun showLocationAlertDialog() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Localización desactivada")
            .setMessage("La localización está apagada. ¿Deseas activarla?")
            .setPositiveButton("Sí") { _, _ ->
                // Abrir la configuración de ubicación del dispositivo
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivity(intent)
            }
            .setNegativeButton("No") { _, _ ->
                // El usuario no quiere activar la localización, mostrar un mensaje o cerrar la aplicación
                Toast.makeText(activity, "La localización está apagada", Toast.LENGTH_SHORT).show()
                activity.finish()
            }
            .setCancelable(false)
            .show()
    }


}