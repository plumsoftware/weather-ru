package ru.plumsoftware.weatherforecastru.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import ru.plumsoftware.weatherforecastru.data.repository.OwmRepository
import ru.plumsoftware.weatherforecastru.data.repository.OwmRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.repository.WeatherApiRepository
import ru.plumsoftware.weatherforecastru.data.repository.WeatherApiRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.storage.HttpClientStorage
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetOwmUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetWeatherApiUseCase

internal val httpClientModel = module {
    single<OwmRepository> {
        OwmRepositoryImpl(
            client = HttpClient(CIO) {
//                install(Logging) {
//                    level = LogLevel.ALL
//                }

                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            prettyPrint = true
                            isLenient = true
                        }
                    )
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 30000
                }
            },
            sharedPreferencesStorage = get()
        )
    }

    single<WeatherApiRepository> {
        WeatherApiRepositoryImpl(
            client = HttpClient(CIO) {
//                install(Logging) {
//                    level = LogLevel.ALL
//                }

                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            prettyPrint = true
                            isLenient = true
                        }
                    )
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 30000
                }
            },
            sharedPreferencesStorage = get()
        )
    }

    factory<HttpClientStorage> {
        HttpClientStorage(
            getOwmUseCase = GetOwmUseCase(
                owmRepository = get()
            ),
            getWeatherApiUseCase = GetWeatherApiUseCase(
                weatherApiRepository = get()
            )
        )
    }
}