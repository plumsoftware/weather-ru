package ru.plumsoftware.weatherforecast.domain.repository

import ru.plumsoftware.weatherforecast.domain.remote.dto.either.WeatherEither

interface OwmRepository {
    suspend fun <D, E, R> getOwm(api: String): WeatherEither<D, E, R>
}