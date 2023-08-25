package ru.plumsoftware.weatherforecast.domain.usecase.settings

import ru.plumsoftware.weatherforecast.domain.models.settings.UserSettings
import ru.plumsoftware.weatherforecast.domain.repository.SharedPreferencesRepository

class GetUserSettingsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(): UserSettings {
        val userSettings: UserSettings = sharedPreferencesRepository.getUserSettings()
        return userSettings
    }
}