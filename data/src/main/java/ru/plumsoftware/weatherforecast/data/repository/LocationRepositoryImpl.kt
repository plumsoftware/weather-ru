package ru.plumsoftware.weatherforecast.data.repository

import android.content.Context
import ru.plumsoftware.weatherforecast.data.location.LocationHelper
import ru.plumsoftware.weatherforecast.domain.models.Location
import ru.plumsoftware.weatherforecast.domain.models.LocationCoords
import ru.plumsoftware.weatherforecast.data.utilities.showToast
import ru.plumsoftware.weatherforecast.domain.repository.LocationRepository

class LocationRepositoryImpl(private val context: Context) : LocationRepository {

    override suspend fun getCurrentLocation(): Location {
        val locationHelper: LocationHelper = LocationHelper(context = context)
        var location: Location = Location(
            city = "",
            country = "",
            coords = LocationCoords(latitude = 1.0, longitude = 1.0)
        )
        if (locationHelper.isLocationEnabled()) {
            locationHelper.getCurrentLocation { latitude, longitude, city, country ->
                location = Location(
                    city = city!!,
                    country = country!!,
                    coords = LocationCoords(latitude = latitude, longitude = longitude)
                )
            }
            return location
        } else {
            showToast(
                context,
                "Определение местоположения не доступно на вашем устройстве"
            )
            return location
        }
    }
}