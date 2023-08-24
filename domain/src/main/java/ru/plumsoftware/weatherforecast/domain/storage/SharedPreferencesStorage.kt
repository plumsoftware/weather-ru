package ru.plumsoftware.weatherforecast.domain.storage

import ru.plumsoftware.weatherforecast.domain.models.UserSettings
import ru.plumsoftware.weatherforecast.domain.usecase.settings.GetUserSettingsUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.SaveUserSettingsAppThemeUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.settings.SaveUserSettingsUseCase

class SharedPreferencesStorage
    (
    private val getUserSettingsUseCase: GetUserSettingsUseCase,
    private val saveUserSettingsUseCase: SaveUserSettingsUseCase,
    private val saveUserSettingsAppThemeUseCase: SaveUserSettingsAppThemeUseCase
) {
    fun get(): UserSettings {
        val execute = getUserSettingsUseCase.execute()
        return execute
    }

    fun save(userSettings: UserSettings) {
        saveUserSettingsUseCase.execute(userSettings = userSettings)
    }

    fun save(applicationTheme: Boolean) {
        saveUserSettingsAppThemeUseCase.execute(appTheme = applicationTheme)
    }
}