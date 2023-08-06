package ru.plumsoftware.weatherforecast.data.repository

import android.content.Context
import android.content.SharedPreferences
import ru.plumsoftware.weatherforecast.data.constants.Constants
import ru.plumsoftware.weatherforecast.data.utilities.logd
import ru.plumsoftware.weatherforecast.domain.models.UserSettings
import ru.plumsoftware.weatherforecast.domain.repository.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl(private val context: Context) : SharedPreferencesRepository {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            Constants.SharedPreferences.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    override fun saveUserSettings(userSettings: UserSettings) {
        logd("application theme: " + if (userSettings.isDarkTheme) "DARK" else "LIGHT")
        sharedPreferences.edit()
            .putBoolean(Constants.SharedPreferences.SHARED_PREF_THEME, userSettings.isDarkTheme)
            .apply()
    }

    override fun getUserSettings(): UserSettings {
        val theme =
            sharedPreferences.getBoolean(Constants.SharedPreferences.SHARED_PREF_THEME, false)
        logd("application theme: " + if (theme) "DARK" else "LIGHT")
        return UserSettings(isDarkTheme = theme)
    }
}