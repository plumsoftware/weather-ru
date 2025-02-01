package ru.plumsoftware.weatherforecastru.data.remote.either

data class WeatherEither<out D, out E, out R>(
    val data: D,
    val httpStatusCode: E,
    val responseTime: R
)