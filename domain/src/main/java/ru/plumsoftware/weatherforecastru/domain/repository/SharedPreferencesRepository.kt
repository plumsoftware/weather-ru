package ru.plumsoftware.weatherforecastru.domain.repository

import ru.plumsoftware.weatherforecastru.domain.models.location.Location
import ru.plumsoftware.weatherforecastru.domain.models.settings.UserSettings
import ru.plumsoftware.weatherforecastru.domain.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.domain.models.settings.WindSpeed
import ru.plumsoftware.weatherforecastru.domain.models.widget.WidgetConfig

interface SharedPreferencesRepository {
    fun saveUserSettings(userSettings: UserSettings)
    fun getUserSettings(): UserSettings
    fun getShowTips() : Boolean
    fun getWidgetConfig() : WidgetConfig
    fun getFirst() : Boolean

    fun saveUserSettingsAppTheme(appTheme: Boolean)
    fun saveUserSettingsShowTips(showTips: Boolean)
    fun saveUserSettingsWeatherUnits(weatherUnits: WeatherUnits)
    fun saveUserSettingsWindSpeed(windSpeed: WindSpeed)
    fun saveLocation(location: Location)
    fun saveWidgetConfig(widgetConfig: WidgetConfig)
    fun saveFist(first: Boolean)
}