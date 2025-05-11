package ru.plumsoftware.weatherforecastru.data.usecase.weather

import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither
import ru.plumsoftware.weatherforecastru.data.repository.OwmRepository

class GetHourlyUseCase(private val owmRepository: OwmRepository) {
    suspend fun <D, E, R> execute () : WeatherEither<D, E, R> {
        val weatherEither: WeatherEither<D, E, R> = owmRepository.getOwmHourly()
        return weatherEither
    }
}