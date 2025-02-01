package ru.plumsoftware.weatherforecastru.data.models.settings

import ru.plumsoftware.weatherforecastru.data.constants.Constants

data class UserSettings(
    val isDarkTheme: Boolean,
    val city: String? = null,
    val country: String? = null,
    val showTips: Boolean = false,
    val weatherUnits: WeatherUnits = WeatherUnits(
        unitsPresentation = Constants.Settings.METRIC.second,
        unitsValue = Constants.Settings.METRIC.first
    ),
    val windSpeed: WindSpeed = WindSpeed(
        windPresentation = Constants.Settings.M_S.first,
        windValue = Constants.Settings.M_S.second
    ),
    val notificationItem: NotificationItem = NotificationItem(
        namingResId = -1,
        period = 21600000
    ),
)