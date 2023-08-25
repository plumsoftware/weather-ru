package ru.plumsoftware.weatherforecast.domain.usecase.settings

import ru.plumsoftware.weatherforecast.domain.models.weathermodels.WeatherUnits
import ru.plumsoftware.weatherforecast.domain.repository.SharedPreferencesRepository

class SaveUserSettingsWeatherUnitsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(weatherUnits: WeatherUnits) {
        with(weatherUnits) {
            sharedPreferencesRepository.saveUserSettingsWeatherUnits(weatherUnits = weatherUnits)
        }
    }
}