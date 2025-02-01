package ru.plumsoftware.weatherforecastru.data.repository

import ru.plumsoftware.weatherforecastru.data.models.location.Location

interface LocationRepository {
    suspend fun getCurrentLocation(): Location
}