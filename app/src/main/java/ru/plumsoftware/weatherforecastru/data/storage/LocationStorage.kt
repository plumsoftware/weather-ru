package ru.plumsoftware.weatherforecastru.data.storage

import ru.plumsoftware.weatherforecastru.data.models.location.Location

class LocationStorage(private val getLastKnownLocationUseCase: ru.plumsoftware.weatherforecastru.data.usecase.location.GetLastKnownLocationUseCase) {
    suspend fun get() : Location {
        val execute = getLastKnownLocationUseCase.execute()
        return execute
    }
}