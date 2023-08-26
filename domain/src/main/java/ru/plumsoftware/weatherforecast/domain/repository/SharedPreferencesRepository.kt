package ru.plumsoftware.weatherforecast.domain.repository

import ru.plumsoftware.weatherforecast.domain.models.location.Location
import ru.plumsoftware.weatherforecast.domain.models.settings.UserSettings
import ru.plumsoftware.weatherforecast.domain.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecast.domain.models.settings.WindSpeed

interface SharedPreferencesRepository {
    fun saveUserSettings(userSettings: UserSettings)
    fun getUserSettings(): UserSettings
    fun getShowTips() : Boolean

    fun saveUserSettingsAppTheme(appTheme: Boolean)
    fun saveUserSettingsShowTips(showTips: Boolean)
    fun saveUserSettingsWeatherUnits(weatherUnits: WeatherUnits)
    fun saveUserSettingsWindSpeed(windSpeed: WindSpeed)
    fun saveLocation(location: Location)
}