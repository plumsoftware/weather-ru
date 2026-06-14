package ru.plumsoftware.weatherforecastru.data.repository

import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither

interface WeatherApiRepository {
    suspend fun <D, E, R> getCurrent(): WeatherEither<D, E, R>
    suspend fun <D, E, R> getForecast(): WeatherEither<D, E, R>
    suspend fun <D, E, R> search(query: String): WeatherEither<D, E, R>
    suspend fun <D, E, R> getAstronomy(date: String): WeatherEither<D, E, R>
}
