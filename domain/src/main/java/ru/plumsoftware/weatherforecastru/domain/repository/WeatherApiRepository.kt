package ru.plumsoftware.weatherforecastru.domain.repository

import ru.plumsoftware.weatherforecastru.domain.remote.dto.either.WeatherEither

interface WeatherApiRepository {
    suspend fun <D, E, R> getWeatherApi(): WeatherEither<D, E, R>
}