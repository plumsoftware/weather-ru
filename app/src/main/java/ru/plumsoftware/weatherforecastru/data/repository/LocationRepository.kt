package ru.plumsoftware.weatherforecastru.data.repository

import ru.plumsoftware.weatherforecastru.data.models.location.Location
import ru.plumsoftware.weatherforecastru.data.models.location.LocationDetectionResult

interface LocationRepository {
    suspend fun getCurrentLocation(): Location

    suspend fun detectLocation(): LocationDetectionResult
}
