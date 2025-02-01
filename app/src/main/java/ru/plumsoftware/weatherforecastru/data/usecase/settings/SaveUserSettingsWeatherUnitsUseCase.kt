package ru.plumsoftware.weatherforecastru.data.usecase.settings

import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository
import ru.plumsoftware.weatherforecastru.data.models.settings.WeatherUnits

class SaveUserSettingsWeatherUnitsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(weatherUnits: WeatherUnits) {
        with(weatherUnits) {
            sharedPreferencesRepository.saveUserSettingsWeatherUnits(weatherUnits = weatherUnits)
        }
    }
}