package com.example.pucmm_map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

import android.widget.ImageView


class MainActivity : ComponentActivity() {

    private lateinit var imageHandler: ImageHandler
    private lateinit var locationPermissionHandler: LocationPermissionHandler
    private lateinit var realTimeLocationHandler: RealTimeLocationHandler


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ////////////////////// MAPA DEL CAMPUS ////////////////////////////////////
        val imageView: ImageView = findViewById(R.id.map_image)
        imageHandler = ImageHandler(imageView)


        ///////////////// GESTOR Y VERIFICACION DE PERMISO DE UBICACION /////////////
        locationPermissionHandler = LocationPermissionHandler(this)
        locationPermissionHandler.checkAndRequestLocationPermissions()
        locationPermissionHandler.checkLocationEnabled()

        ///////////////// GESTOR DE UBICACION EN TIEMPO REAL //////////////////////////
        realTimeLocationHandler = RealTimeLocationHandler(this)

        //////////////////////////CONVERTIR GPS-IMAGE//////////////////////////////////
        val gpsPoints = listOf(
            GPSPoint(1074.2567, -2505.6869, 18.462529, -69.929783),
            GPSPoint(441.90713, -1198.4565, 18.463363, -69.930143),
            GPSPoint(751.638743, -561.694034, 18.463792, -69.929945),
            GPSPoint(921.350327, -622.305314, 18.463757, -69.929808),
            GPSPoint(1365.9247, -77.061101, 18.4623678, -69.929524),
            GPSPoint(1637.8543, -1196.5845, 18.463399, -69.929336),
            GPSPoint(1738.8057, -1407.1468, 18.463272, -69.929264),
            GPSPoint(503.239923, -1788.9648, 18.462995, -69.929124)
        )

        val gpsImageConverter = GPSImageConverter(gpsPoints)
        val lat = 18.46345330199342

        val lon =-69.92972315242271


        val imagePoint: PointF = gpsImageConverter.convertGPSPointToImagePoint(lat, lon)
        Log.d("MainActivity", "Image Coordinates: (${imagePoint.x}, ${imagePoint.y})")




    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHandler.handlePermissionsResult(requestCode, grantResults)

        // ACTUALIZACIONES DE UBICACIONES
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            realTimeLocationHandler.startLocationUpdates()
        }
    }


}

