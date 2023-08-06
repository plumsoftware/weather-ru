package ru.plumsoftware.weatherforecast.domain.repository

import ru.plumsoftware.weatherforecast.domain.models.Location

interface LocationRepository {
    suspend fun getCurrentLocation(): Location
}