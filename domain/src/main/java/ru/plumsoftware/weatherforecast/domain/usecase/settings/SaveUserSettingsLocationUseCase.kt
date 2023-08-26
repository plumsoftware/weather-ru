package ru.plumsoftware.weatherforecast.domain.usecase.settings

import ru.plumsoftware.weatherforecast.domain.models.location.Location
import ru.plumsoftware.weatherforecast.domain.repository.SharedPreferencesRepository

class SaveUserSettingsLocationUseCase (private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute (value: Location) {
        sharedPreferencesRepository.saveLocation(location = value)
    }
}