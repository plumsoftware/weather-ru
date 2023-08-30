package ru.plumsoftware.weatherforecast.domain.usecase

import ru.plumsoftware.weatherforecast.domain.remote.dto.either.OwmEither
import ru.plumsoftware.weatherforecast.domain.repository.OwmRepository

class GetOwmUseCase (private val owmRepository: OwmRepository) {
    suspend fun <D, E, R> execute () : OwmEither<D, E, R> {
        val owmEither: OwmEither<D, E, R> = owmRepository.getOwm<D, E, R>()
        return owmEither
    }
}