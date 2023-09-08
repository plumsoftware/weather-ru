package ru.plumsoftware.weatherforecastru.domain.usecase.settings

import ru.plumsoftware.weatherforecastru.domain.models.settings.UserSettings
import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

class SaveUserSettingsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(userSettings: UserSettings) {
        sharedPreferencesRepository.saveUserSettings(userSettings = userSettings)
    }
}