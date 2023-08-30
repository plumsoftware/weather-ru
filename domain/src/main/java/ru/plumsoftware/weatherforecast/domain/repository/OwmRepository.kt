package ru.plumsoftware.weatherforecast.domain.repository

import ru.plumsoftware.weatherforecast.domain.remote.dto.either.OwmEither

interface OwmRepository {
    suspend fun <D, E, R> getOwm(): OwmEither<D, E, R>
}