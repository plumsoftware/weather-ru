package ru.plumsoftware.weatherforecastru.domain.storage

import ru.plumsoftware.weatherforecastru.domain.models.location.Location
import ru.plumsoftware.weatherforecastru.domain.usecase.location.GetLastKnownLocationUseCase

class LocationStorage(private val getLastKnownLocationUseCase: GetLastKnownLocationUseCase) {
    suspend fun get() : Location {
        val execute = getLastKnownLocationUseCase.execute()
        return execute
    }
}