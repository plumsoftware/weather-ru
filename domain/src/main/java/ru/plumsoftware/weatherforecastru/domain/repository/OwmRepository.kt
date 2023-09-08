package ru.plumsoftware.weatherforecastru.domain.repository

import ru.plumsoftware.weatherforecastru.domain.remote.dto.either.WeatherEither

interface OwmRepository {
    suspend fun <D, E, R> getOwm(): WeatherEither<D, E, R>
}