package ru.plumsoftware.weatherforecast.data.repository

import ru.plumsoftware.weatherforecast.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecast.domain.repository.OwmRepository

class OwmRepositoryImpl : OwmRepository {
    override suspend fun <T> getOwm(): T {
        return OwmResponse(
            base = "Hello, everything is working now!"
        ) as T
    }
}