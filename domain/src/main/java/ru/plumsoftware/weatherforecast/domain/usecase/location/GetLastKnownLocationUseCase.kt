package ru.plumsoftware.weatherforecast.domain.usecase.location

import ru.plumsoftware.weatherforecast.domain.models.location.Location
import ru.plumsoftware.weatherforecast.domain.repository.LocationRepository

class GetLastKnownLocationUseCase(private val locationRepository: LocationRepository) {
    suspend fun execute () : Location {
        val location = locationRepository.getCurrentLocation()
        return location
    }
}