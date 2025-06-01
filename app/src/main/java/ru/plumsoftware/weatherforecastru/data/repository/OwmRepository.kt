package ru.plumsoftware.weatherforecastru.data.repository

import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither

interface OwmRepository {
    suspend fun <D, E, R> getOwm(): WeatherEither<D, E, R>
    suspend fun <D, E, R> getOwmHourly(): WeatherEither<D, E, R>
}