package ru.plumsoftware.weatherforecastru.domain.usecase.weather

import ru.plumsoftware.weatherforecastru.domain.remote.dto.either.WeatherEither
import ru.plumsoftware.weatherforecastru.domain.repository.OwmRepository

class GetOwmUseCase (private val owmRepository: OwmRepository) {
    suspend fun <D, E, R> execute () : WeatherEither<D, E, R> {
        val weatherEither: WeatherEither<D, E, R> = owmRepository.getOwm<D, E, R>()
        return weatherEither
    }
}