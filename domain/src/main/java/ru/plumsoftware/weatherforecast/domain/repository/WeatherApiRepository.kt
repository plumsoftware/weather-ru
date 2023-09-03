package ru.plumsoftware.weatherforecast.domain.repository

import ru.plumsoftware.weatherforecast.domain.remote.dto.either.WeatherEither

interface WeatherApiRepository {
    suspend fun <D, E, R> getWeatherApi(): WeatherEither<D, E, R>
}