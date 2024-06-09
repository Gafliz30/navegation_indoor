package com.example.pucmm_map

import android.graphics.PointF


data class GPSPoint(val xImage: Double, val yImage: Double, val lat: Double, val lon: Double)

class GPSImageConverter(private val gpsPoints: List<GPSPoint>) {

    private val gpsPointsSortedByX = gpsPoints.sortedBy { it.xImage }

    fun convertGPSPointToImagePoint(lat: Double, lon: Double): PointF {
        // Find the closest points of reference to the left and right of the GPS point
        val leftPoint = gpsPointsSortedByX.findLast { it.lat <= lat } ?: gpsPointsSortedByX.first()
        val rightPoint = gpsPointsSortedByX.find { it.lat >= lat } ?: gpsPointsSortedByX.last()

        // Calculate the distance between the GPS point and the points of reference
        val distanceLeft = distance(lat, lon, leftPoint.lat, leftPoint.lon)
        val distanceRight = distance(lat, lon, rightPoint.lat, rightPoint.lon)

        // Calculate the position of the point in the image using linear interpolation
        val xImage = leftPoint.xImage + (rightPoint.xImage - leftPoint.xImage) * distanceLeft / (distanceLeft + distanceRight)
        val yImage = leftPoint.yImage + (rightPoint.yImage - leftPoint.yImage) * distanceLeft / (distanceLeft + distanceRight)

        return PointF(xImage.toFloat(), yImage.toFloat())
    }

    private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371.0 // Radius of the Earth in kilometers
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return earthRadius * c
    }
}