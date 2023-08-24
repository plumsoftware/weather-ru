package ru.plumsoftware.weatherforecast.domain.repository

import ru.plumsoftware.weatherforecast.domain.models.UserSettings

interface SharedPreferencesRepository {
    fun saveUserSettings(userSettings: UserSettings)
    fun getUserSettings() : UserSettings

    fun saveUserSettingsAppTheme(appTheme: Boolean)
}