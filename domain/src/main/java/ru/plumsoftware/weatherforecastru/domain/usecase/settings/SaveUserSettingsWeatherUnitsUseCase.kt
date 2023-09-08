package ru.plumsoftware.weatherforecastru.domain.usecase.settings

import ru.plumsoftware.weatherforecastru.domain.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

class SaveUserSettingsWeatherUnitsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(weatherUnits: WeatherUnits) {
        with(weatherUnits) {
            sharedPreferencesRepository.saveUserSettingsWeatherUnits(weatherUnits = weatherUnits)
        }
    }
}