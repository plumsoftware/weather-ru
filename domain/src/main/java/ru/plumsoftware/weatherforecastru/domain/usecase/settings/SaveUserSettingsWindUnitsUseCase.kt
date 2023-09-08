package ru.plumsoftware.weatherforecastru.domain.usecase.settings

import ru.plumsoftware.weatherforecastru.domain.models.settings.WindSpeed
import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

class SaveUserSettingsWindUnitsUseCase (private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute (value: WindSpeed){
        sharedPreferencesRepository.saveUserSettingsWindSpeed(windSpeed = value)
    }
}