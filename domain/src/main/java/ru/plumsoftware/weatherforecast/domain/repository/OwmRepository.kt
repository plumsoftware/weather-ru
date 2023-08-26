package ru.plumsoftware.weatherforecast.domain.repository

interface OwmRepository {

    suspend fun <T> getOwm() : T
}