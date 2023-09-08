package ru.plumsoftware.weatherforecastru.domain.repository

import ru.plumsoftware.weatherforecastru.domain.models.location.Location

interface LocationRepository {
    suspend fun getCurrentLocation(): Location
}