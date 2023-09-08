package ru.plumsoftware.weatherforecastru.domain.usecase.weather

import ru.plumsoftware.weatherforecastru.domain.remote.dto.either.WeatherEither
import ru.plumsoftware.weatherforecastru.domain.repository.WeatherApiRepository

class GetWeatherApiUseCase(
    private val weatherApiRepository: WeatherApiRepository
) {
    suspend fun <D, E, R> execute () : WeatherEither<D, E, R> {
        val weatherEither: WeatherEither<D, E, R> = weatherApiRepository.getWeatherApi<D, E, R>()
        return weatherEither
    }
}