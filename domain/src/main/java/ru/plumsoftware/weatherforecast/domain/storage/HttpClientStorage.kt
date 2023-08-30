package ru.plumsoftware.weatherforecast.domain.storage

import ru.plumsoftware.weatherforecast.domain.remote.dto.either.OwmEither
import ru.plumsoftware.weatherforecast.domain.usecase.GetOwmUseCase

class HttpClientStorage(private val getOwmUseCase: GetOwmUseCase) {
    suspend fun <D, E, R> get() : OwmEither<D, E, R> {
        val execute = getOwmUseCase.execute<D, E, R>()
        return execute
    }
}