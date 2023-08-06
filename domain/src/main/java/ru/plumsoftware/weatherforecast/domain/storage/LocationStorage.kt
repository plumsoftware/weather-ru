package ru.plumsoftware.weatherforecast.domain.storage

import ru.plumsoftware.weatherforecast.domain.models.Location
import ru.plumsoftware.weatherforecast.domain.usecase.location.GetLastKnownLocationUseCase

class LocationStorage(private val getLastKnownLocationUseCase: GetLastKnownLocationUseCase) {
    suspend fun get() : Location {
        val execute = getLastKnownLocationUseCase.execute()
        return execute
    }
}