package ru.plumsoftware.weatherforecastru.data.repository

import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither

interface WeatherApiRepository {
    suspend fun <D, E, R> getWeatherApi(): WeatherEither<D, E, R>
}