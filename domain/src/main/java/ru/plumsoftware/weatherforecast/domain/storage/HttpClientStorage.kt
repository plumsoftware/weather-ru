package ru.plumsoftware.weatherforecast.domain.storage

import ru.plumsoftware.weatherforecast.domain.remote.dto.either.WeatherEither
import ru.plumsoftware.weatherforecast.domain.usecase.weather.GetOwmUseCase
import ru.plumsoftware.weatherforecast.domain.usecase.weather.GetWeatherApiUseCase

class HttpClientStorage(
    private val getOwmUseCase: GetOwmUseCase,
    private val getWeatherApiUseCase: GetWeatherApiUseCase
) {
    suspend fun <D, E, R> get(api: String): WeatherEither<D, E, R> {
        val execute = getOwmUseCase.execute<D, E, R>(api = api)
        return execute
    }

    suspend fun <D, E, R> getWeatherApi(): WeatherEither<D, E, R> {
        val execute = getWeatherApiUseCase.execute<D, E, R>()
        return execute
    }
}