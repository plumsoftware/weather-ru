package ru.plumsoftware.weatherforecastru.domain.usecase.settings

import ru.plumsoftware.weatherforecastru.domain.models.settings.UserSettings
import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

class GetUserSettingsUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(): UserSettings {
        val userSettings: UserSettings = sharedPreferencesRepository.getUserSettings()
        return userSettings
    }
}