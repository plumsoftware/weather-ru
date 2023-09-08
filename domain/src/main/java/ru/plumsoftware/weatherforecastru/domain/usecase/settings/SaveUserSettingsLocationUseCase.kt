package ru.plumsoftware.weatherforecastru.domain.usecase.settings

import ru.plumsoftware.weatherforecastru.domain.models.location.Location
import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

class SaveUserSettingsLocationUseCase (private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute (value: Location) {
        sharedPreferencesRepository.saveLocation(location = value)
    }
}