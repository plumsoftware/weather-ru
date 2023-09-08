package ru.plumsoftware.weatherforecastru.domain.usecase.location

import ru.plumsoftware.weatherforecastru.domain.models.location.Location
import ru.plumsoftware.weatherforecastru.domain.repository.LocationRepository

class GetLastKnownLocationUseCase(private val locationRepository: LocationRepository) {
    suspend fun execute () : Location {
        val location = locationRepository.getCurrentLocation()
        return location
    }
}