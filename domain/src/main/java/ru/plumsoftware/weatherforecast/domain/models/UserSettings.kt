package ru.plumsoftware.weatherforecast.domain.models

import ru.plumsoftware.weatherforecast.domain.models.weathermodels.WeatherUnits
import ru.plumsoftware.weatherforecast.domain.constants.Constants
import ru.plumsoftware.weatherforecast.domain.models.weathermodels.WindSpeed

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