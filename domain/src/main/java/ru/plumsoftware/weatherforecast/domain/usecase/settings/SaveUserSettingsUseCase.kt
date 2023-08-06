package ru.plumsoftware.weatherforecast.domain.usecase.settings

import ru.plumsoftware.weatherforecast.domain.models.UserSettings
import ru.plumsoftware.weatherforecast.domain.repository.SharedPreferencesRepository

class SaveUserSettingsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(userSettings: UserSettings) {
        sharedPreferencesRepository.saveUserSettings(userSettings = userSettings)
    }
}