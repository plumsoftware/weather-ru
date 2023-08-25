package ru.plumsoftware.weatherforecast.domain.usecase.settings

import ru.plumsoftware.weatherforecast.domain.models.weathermodels.WindSpeed
import ru.plumsoftware.weatherforecast.domain.repository.SharedPreferencesRepository

class SaveUserSettingsWindUnitsUseCase (private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute (value: WindSpeed){
        sharedPreferencesRepository.saveUserSettingsWindSpeed(windSpeed = value)
    }
}