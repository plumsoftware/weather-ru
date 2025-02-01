package ru.plumsoftware.weatherforecastru.data.usecase.settings

import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository
import ru.plumsoftware.weatherforecastru.data.models.location.Location

class SaveUserSettingsLocationUseCase (private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute (value: Location) {
        sharedPreferencesRepository.saveLocation(location = value)
    }
}