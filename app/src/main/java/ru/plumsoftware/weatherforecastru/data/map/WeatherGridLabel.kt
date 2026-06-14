package ru.plumsoftware.weatherforecastru.data.map

data class WeatherGridLabel(
    val latitude: Double,
    val longitude: Double,
    val text: String,
)

data class MapPointWeather(
    val temperatureC: Int?,
    val cloudinessPercent: Int?,
    val windSpeed: Double?,
)
