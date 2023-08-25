package ru.plumsoftware.weatherforecast.domain.repository

import ru.plumsoftware.weatherforecast.domain.models.location.Location

interface LocationRepository {
    suspend fun getCurrentLocation(): Location
}