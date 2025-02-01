package ru.plumsoftware.weatherforecastru.data.usecase.settings

import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository
import ru.plumsoftware.weatherforecastru.data.models.settings.WindSpeed

class SaveUserSettingsWindUnitsUseCase (private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute (value: WindSpeed){
        sharedPreferencesRepository.saveUserSettingsWindSpeed(windSpeed = value)
    }
}