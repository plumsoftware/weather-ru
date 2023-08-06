package ru.plumsoftware.weatherforecast.domain.usecase

import ru.plumsoftware.weatherforecast.domain.models.Location
import ru.plumsoftware.weatherforecast.domain.repository.LocationRepository

class GetLastKnownLocationUseCase(private val locationRepository: LocationRepository) {
    suspend fun execute () : Location{
        val location = locationRepository.getCurrentLocation()
        return location
    }
}