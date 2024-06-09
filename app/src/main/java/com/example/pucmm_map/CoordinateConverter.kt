package com.example.pucmm_map

class CoordinateConverter {

    private val referenceTable = mapOf(
        "Esquina de a1" to Pair(18.463645031092906, -69.9295648545611),
        "Esquina de a1" to Pair(18.463065738413675, -69.92918556535734),
        "Esquina de a1" to Pair(18.462940765149114, -69.92934208767907),
        "Esquina de a1" to Pair(18.463596077977137, -69.92970130018108),
        "Esquina de octa" to Pair(18.46360755278952, -69.93044583441014),
        "salida" to Pair(18.463784700279987, -69.92943052322146),
        "entrada" to Pair(18.46250756492505, -69.92981847995284),
        "administrativo" to Pair(18.46298610677418, -69.93011301504433),
        "esquina" to Pair(18.463247212221173, -69.92959671968688),
        "biblioteca" to Pair(18.463264414331807, -69.92986383272314)
    )

    private val imageCoordinatesTable = mapOf(
        "Esquina de a1" to Pair(901.54, 568.75),
        "Esquina de a1" to Pair(940.26, 1296.62),
        "Esquina de a1" to Pair(744.52, 1339.51),
        "Esquina de a1" to Pair(746.35, 553.86),
        "Esquina de octa" to Pair(114.06, 206.68),
        "salida" to Pair(1124.29, 454.69),
        "entrada" to Pair(147.77, 1543.51),
        "administrativo" to Pair(90.33, 935.79),
        "esquina" to Pair(667.39, 934.91),
        "biblioteca" to Pair(475.58, 742.63)
    )

    fun convertToImageCoordinates(locationName: String, latitude: Double, longitude: Double): Pair<Double, Double>? {
        val referenceCoordinates = referenceTable[locationName] ?: return null
        val imageCoordinates = imageCoordinatesTable[locationName] ?: return null

        val latDiff = latitude - referenceCoordinates.first
        val lonDiff = longitude - referenceCoordinates.second

        // Se ajustan los factores de conversión según sea necesario
        val x = imageCoordinates.first + (latDiff * 1000000) // Ajuste en factor para x
        val y = imageCoordinates.second + (lonDiff * 1000000) // Ajuste en factor para y

        return Pair(x, y)
    }
}
