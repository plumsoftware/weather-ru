package ru.plumsoftware.weatherforecast.domain.usecase.settings

import ru.plumsoftware.weatherforecast.domain.models.settings.WindSpeed
import ru.plumsoftware.weatherforecast.domain.repository.SharedPreferencesRepository

class SaveUserSettingsWindUnitsUseCase (private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute (value: WindSpeed){
        sharedPreferencesRepository.saveUserSettingsWindSpeed(windSpeed = value)
    }
}