package ru.plumsoftware.weatherforecastru.data.map

import org.osmdroid.util.GeoPoint
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.sqrt

object MapGridCalculator {
    const val GRID_SPACING_METERS = 2_500.0
    const val MAP_RADIUS_METERS = 3_000.0

    fun buildGridPoints(centerLatitude: Double, centerLongitude: Double): List<GeoPoint> {
        if (centerLatitude == 0.0 && centerLongitude == 0.0) return emptyList()

        val latDegreesPerMeter = 1.0 / 111_320.0
        val lonDegreesPerMeter = 1.0 / (111_320.0 * cos(Math.toRadians(centerLatitude)).coerceAtLeast(0.0001))

        val steps = ceil(MAP_RADIUS_METERS / GRID_SPACING_METERS).toInt()
        val points = LinkedHashSet<GeoPoint>()

        for (row in -steps..steps) {
            for (column in -steps..steps) {
                val offsetXMeters = column * GRID_SPACING_METERS
                val offsetYMeters = row * GRID_SPACING_METERS
                val distanceMeters = sqrt(
                    offsetXMeters * offsetXMeters + offsetYMeters * offsetYMeters,
                )
                if (distanceMeters > MAP_RADIUS_METERS) continue

                val latitude = centerLatitude + offsetYMeters * latDegreesPerMeter
                val longitude = centerLongitude + offsetXMeters * lonDegreesPerMeter
                points.add(GeoPoint(latitude, longitude))
            }
        }

        return points.toList()
    }
}
