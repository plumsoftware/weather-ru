package ru.plumsoftware.weatherforecast.domain.repository

import ru.plumsoftware.weatherforecast.domain.models.UserSettings
import ru.plumsoftware.weatherforecast.domain.models.weathermodels.WeatherUnits
import ru.plumsoftware.weatherforecast.domain.models.weathermodels.WindSpeed

interface SharedPreferencesRepository {
    fun saveUserSettings(userSettings: UserSettings)
    fun getUserSettings(): UserSettings
    fun getShowTips() : Boolean

    fun saveUserSettingsAppTheme(appTheme: Boolean)
    fun saveUserSettingsShowTips(showTips: Boolean)
    fun saveUserSettingsWeatherUnits(weatherUnits: WeatherUnits)
    fun saveUserSettingsWindSpeed(windSpeed: WindSpeed)
}