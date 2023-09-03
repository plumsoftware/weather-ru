package ru.plumsoftware.weatherforecast.domain.remote.dto.either


data class WeatherEither<out D, out E, out R>(
    val data: D,
    val httpStatusCode: E,
    val responseTime: R
)