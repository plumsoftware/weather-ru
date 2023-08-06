package ru.plumsoftware.weatherforecast.domain.models

data class UserSettings(
    val isDarkTheme: Boolean,
    val city: String? = null
)