package ru.plumsoftware.weatherforecastru.data.usecase.settings

import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository
import ru.plumsoftware.weatherforecastru.data.models.settings.UserSettings

class GetUserSettingsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(): UserSettings {
        val userSettings: UserSettings = sharedPreferencesRepository.getUserSettings()
        return userSettings
    }
}