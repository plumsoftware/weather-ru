package ru.plumsoftware.weatherforecastru.domain.models.settings

import ru.plumsoftware.weatherforecastru.domain.constants.Constants

data class UserSettings(
    val isDarkTheme: Boolean,
    val city: String? = null,
    val country: String? = null,
    val showTips: Boolean = true,
    val weatherUnits: WeatherUnits = WeatherUnits(
        unitsPresentation = Constants.Settings.METRIC.second,
        unitsValue = Constants.Settings.METRIC.first
    ),
    val windSpeed: WindSpeed = WindSpeed(
        windPresentation = Constants.Settings.M_S.first,
        windValue = Constants.Settings.M_S.second
    )
)