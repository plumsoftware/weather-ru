package ru.plumsoftware.weatherforecastru.data.repository

import android.content.Context
import android.location.Geocoder
import ru.plumsoftware.weatherforecastru.data.location.LocationHelper
import ru.plumsoftware.weatherforecastru.data.models.location.Location
import ru.plumsoftware.weatherforecastru.data.models.location.LocationCoords
import java.util.Locale

class LocationRepositoryImpl(private val context: Context) : LocationRepository {

    override suspend fun getCurrentLocation(): Location {
        val locationHelper = LocationHelper(context = context)
        if (!locationHelper.isLocationPermissionGranted() || !locationHelper.isLocationEnabled()) {
            return emptyLocation()
        }

        val deviceLocation = locationHelper.awaitCurrentLocation() ?: return emptyLocation()

        val city = runCatching {
            Geocoder(context, Locale.getDefault())
                .getFromLocation(deviceLocation.latitude, deviceLocation.longitude, 1)
                ?.firstOrNull()
                ?.locality
        }.getOrNull().orEmpty()

        return Location(
            city = city,
            country = "",
            coords = LocationCoords(
                latitude = deviceLocation.latitude,
                longitude = deviceLocation.longitude,
            ),
        )
    }

    private fun emptyLocation(): Location = Location(
        city = "",
        country = "",
        coords = LocationCoords(latitude = 0.0, longitude = 0.0),
    )
}
