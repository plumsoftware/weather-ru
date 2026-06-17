package ru.plumsoftware.weatherforecastru.data.repository

import android.content.Context
import ru.plumsoftware.weatherforecastru.data.location.LocationGeocoder
import ru.plumsoftware.weatherforecastru.data.location.LocationHelper
import ru.plumsoftware.weatherforecastru.data.location.VpnDetector
import ru.plumsoftware.weatherforecastru.data.models.location.Location
import ru.plumsoftware.weatherforecastru.data.models.location.LocationCoords
import ru.plumsoftware.weatherforecastru.data.models.location.LocationDetectionResult

class LocationRepositoryImpl(private val context: Context) : LocationRepository {

    override suspend fun getCurrentLocation(): Location =
        when (val result = detectLocation()) {
            is LocationDetectionResult.Success -> result.location
            else -> emptyLocation()
        }

    override suspend fun detectLocation(): LocationDetectionResult {
        if (VpnDetector.isVpnActive(context)) {
            return LocationDetectionResult.VpnActive
        }

        val locationHelper = LocationHelper(context = context)
        if (!locationHelper.isLocationPermissionGranted()) {
            return LocationDetectionResult.PermissionDenied
        }
        if (!locationHelper.isLocationEnabled()) {
            return LocationDetectionResult.LocationDisabled
        }

        val deviceLocation = locationHelper.awaitCurrentLocation() ?: return LocationDetectionResult.LocationUnavailable

        val city = LocationGeocoder(context).resolveCity(
            latitude = deviceLocation.latitude,
            longitude = deviceLocation.longitude,
        )
        if (city.isBlank()) {
            return LocationDetectionResult.LocationUnavailable
        }

        return LocationDetectionResult.Success(
            location = Location(
                city = city,
                country = "",
                coords = LocationCoords(
                    latitude = deviceLocation.latitude,
                    longitude = deviceLocation.longitude,
                ),
            ),
        )
    }

    private fun emptyLocation(): Location = Location(
        city = "",
        country = "",
        coords = LocationCoords(latitude = 0.0, longitude = 0.0),
    )
}
