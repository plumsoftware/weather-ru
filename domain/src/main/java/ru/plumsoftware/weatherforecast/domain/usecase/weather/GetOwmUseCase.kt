package ru.plumsoftware.weatherforecast.domain.usecase.weather

import ru.plumsoftware.weatherforecast.domain.remote.dto.either.WeatherEither
import ru.plumsoftware.weatherforecast.domain.repository.OwmRepository

class GetOwmUseCase (private val owmRepository: OwmRepository) {
    suspend fun <D, E, R> execute (api: String) : WeatherEither<D, E, R> {
        val weatherEither: WeatherEither<D, E, R> = owmRepository.getOwm<D, E, R>(api = api)
        return weatherEither
    }
}