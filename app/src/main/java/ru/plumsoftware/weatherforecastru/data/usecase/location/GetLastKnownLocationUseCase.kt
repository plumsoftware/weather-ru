package ru.plumsoftware.weatherforecastru.data.usecase.location

import ru.plumsoftware.weatherforecastru.data.repository.LocationRepository
import ru.plumsoftware.weatherforecastru.data.models.location.Location

class GetLastKnownLocationUseCase(private val locationRepository: LocationRepository) {
    suspend fun execute () : Location {
        val location = locationRepository.getCurrentLocation()
        return location
    }
}