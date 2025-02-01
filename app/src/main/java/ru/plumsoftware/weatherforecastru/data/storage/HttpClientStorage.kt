package ru.plumsoftware.weatherforecastru.data.storage

import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetOwmUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetWeatherApiUseCase

class HttpClientStorage(
    private val getOwmUseCase: GetOwmUseCase,
    private val getWeatherApiUseCase: GetWeatherApiUseCase
) {
    suspend fun <D, E, R> get(): WeatherEither<D, E, R> {
        val execute = getOwmUseCase.execute<D, E, R>()
        return execute
    }

    suspend fun <D, E, R> getWeatherApi(): WeatherEither<D, E, R> {
        val execute = getWeatherApiUseCase.execute<D, E, R>()
        return execute
    }
}