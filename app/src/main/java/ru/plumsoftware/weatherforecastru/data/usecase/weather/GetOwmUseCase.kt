package ru.plumsoftware.weatherforecastru.data.usecase.weather

import ru.plumsoftware.weatherforecastru.data.repository.OwmRepository
import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither

class GetOwmUseCase (private val owmRepository: OwmRepository) {
    suspend fun <D, E, R> execute () : WeatherEither<D, E, R> {
        val weatherEither: WeatherEither<D, E, R> = owmRepository.getOwm()
        return weatherEither
    }
}