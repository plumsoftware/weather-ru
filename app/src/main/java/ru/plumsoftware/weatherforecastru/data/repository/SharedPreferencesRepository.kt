package ru.plumsoftware.weatherforecastru.data.repository

import ru.plumsoftware.weatherforecastru.data.models.location.Location
import ru.plumsoftware.weatherforecastru.data.models.settings.NotificationItem
import ru.plumsoftware.weatherforecastru.data.models.settings.UserSettings
import ru.plumsoftware.weatherforecastru.data.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.data.models.settings.WindSpeed
import ru.plumsoftware.weatherforecastru.data.models.widget.WidgetConfig

interface SharedPreferencesRepository {
    fun saveUserSettings(userSettings: UserSettings)
    fun getUserSettings(): UserSettings
    fun getShowTips() : Boolean
    fun getWidgetConfig() : WidgetConfig
    fun getFirst() : Boolean
    fun getNotificationPeriod() : NotificationItem

    fun saveUserSettingsAppTheme(appTheme: Boolean)
    fun saveUserSettingsShowTips(showTips: Boolean)
    fun saveUserSettingsWeatherUnits(weatherUnits: WeatherUnits)
    fun saveUserSettingsWindSpeed(windSpeed: WindSpeed)
    fun saveLocation(location: Location)
    fun saveWidgetConfig(widgetConfig: WidgetConfig)
    fun saveFist(first: Boolean)
    fun saveNotificationPeriod(notificationItem: NotificationItem)
}