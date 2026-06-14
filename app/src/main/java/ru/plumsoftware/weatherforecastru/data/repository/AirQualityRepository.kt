package ru.plumsoftware.weatherforecastru.data.repository

import ru.plumsoftware.weatherforecastru.data.models.airquality.AirQualityData

interface AirQualityRepository {
    suspend fun getAirQuality(lat: Double, lon: Double): AirQualityData
}
