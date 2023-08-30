package ru.plumsoftware.weatherforecast.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import ru.plumsoftware.weatherforecast.data.repository.OwmRepositoryImpl
import ru.plumsoftware.weatherforecast.domain.repository.OwmRepository
import ru.plumsoftware.weatherforecast.domain.storage.HttpClientStorage
import ru.plumsoftware.weatherforecast.domain.usecase.GetOwmUseCase

internal val httpClientModel = module {
    single<OwmRepository> {
        OwmRepositoryImpl(client = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL //TODO() remove later
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
        })
    }

    factory<HttpClientStorage> { HttpClientStorage(getOwmUseCase = GetOwmUseCase(owmRepository = get())) }
}