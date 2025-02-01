package ru.plumsoftware.weatherforecastru.data.usecase.weather

import ru.plumsoftware.weatherforecastru.data.repository.WeatherApiRepository
import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither

class GetWeatherApiUseCase(
    private val weatherApiRepository: WeatherApiRepository
) {
    suspend fun <D, E, R> execute () : WeatherEither<D, E, R> {
        val weatherEither: WeatherEither<D, E, R> = weatherApiRepository.getWeatherApi<D, E, R>()
        return weatherEither
    }
}