package ru.plumsoftware.weatherforecastru.data.usecase.settings

import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository
import ru.plumsoftware.weatherforecastru.data.models.settings.UserSettings

class SaveUserSettingsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(userSettings: UserSettings) {
        sharedPreferencesRepository.saveUserSettings(userSettings = userSettings)
    }
}