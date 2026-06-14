package ru.plumsoftware.weatherforecastru.data.map

import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import kotlin.math.cos

object MapViewportCalculator {
    fun boundingBoxForRadius(center: GeoPoint, radiusMeters: Double): BoundingBox {
        val latDelta = radiusMeters / 111_320.0
        val lonDelta = radiusMeters / (111_320.0 * cos(Math.toRadians(center.latitude)).coerceAtLeast(0.0001))
        return BoundingBox(
            center.latitude + latDelta,
            center.longitude + lonDelta,
            center.latitude - latDelta,
            center.longitude - lonDelta,
        )
    }
}
