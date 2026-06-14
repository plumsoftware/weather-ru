package ru.plumsoftware.weatherforecastru.messanging

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.plumsoftware.weatherforecastru.data.repository.OwmRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.repository.WeatherApiRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.storage.HttpClientStorage
import ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetFirstUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetNotificationItemUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetUserSettingsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveFirstUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveNotificationItemUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsAppThemeUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsLocationUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsWeatherUnitsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsWindUnitsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetHourlyUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetOwmUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetWeatherApiAstronomyUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetWeatherApiCurrentUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetWeatherApiForecastUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.widget.GetWidgetConfigUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.widget.SaveWidgetConfigUseCase

object WeatherNotificationDependencies {

    fun sharedPreferencesStorage(context: Context): SharedPreferencesStorage {
        val repository = SharedPreferencesRepositoryImpl(context = context.applicationContext)
        return SharedPreferencesStorage(
            getUserSettingsUseCase = GetUserSettingsUseCase(sharedPreferencesRepository = repository),
            getUserSettingsShowTipsUseCase = GetUserSettingsShowTipsUseCase(
                sharedPreferencesRepository = repository,
            ),
            getFirstUseCase = GetFirstUseCase(sharedPreferencesRepository = repository),
            saveUserSettingsUseCase = SaveUserSettingsUseCase(sharedPreferencesRepository = repository),
            saveUserSettingsAppThemeUseCase = SaveUserSettingsAppThemeUseCase(
                sharedPreferencesRepository = repository,
            ),
            saveUserSettingsShowTipsUseCase = SaveUserSettingsShowTipsUseCase(
                sharedPreferencesRepository = repository,
            ),
            saveUserSettingsWeatherUnitsUseCase = SaveUserSettingsWeatherUnitsUseCase(
                sharedPreferencesRepository = repository,
            ),
            saveUserSettingsWindUnitsUseCase = SaveUserSettingsWindUnitsUseCase(
                sharedPreferencesRepository = repository,
            ),
            saveUserSettingsLocationUseCase = SaveUserSettingsLocationUseCase(
                sharedPreferencesRepository = repository,
            ),
            saveFirstUseCase = SaveFirstUseCase(sharedPreferencesRepository = repository),
            saveWidgetConfigUseCase = SaveWidgetConfigUseCase(sharedPreferencesRepository = repository),
            getWidgetConfigUseCase = GetWidgetConfigUseCase(sharedPreferencesRepository = repository),
            getNotificationItemUseCase = GetNotificationItemUseCase(sharedPreferencesRepository = repository),
            saveNotificationItemUseCase = SaveNotificationItemUseCase(
                sharedPreferencesRepository = repository,
            ),
        )
    }

    fun httpClientStorage(context: Context): HttpClientStorage {
        val prefs = sharedPreferencesStorage(context)
        val owmRepository = OwmRepositoryImpl(
            client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            prettyPrint = true
                            isLenient = true
                        },
                    )
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 15_000
                    connectTimeoutMillis = 10_000
                    socketTimeoutMillis = 15_000
                }
            },
            sharedPreferencesStorage = prefs,
        )
        val weatherApiRepository = WeatherApiRepositoryImpl(
            client = HttpClient(Android) {
                engine {
                    connectTimeout = 0
                    socketTimeout = 0
                }
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            prettyPrint = true
                            isLenient = true
                        },
                    )
                }
            },
            sharedPreferencesStorage = prefs,
        )
        return HttpClientStorage(
            getOwmUseCase = GetOwmUseCase(owmRepository = owmRepository),
            getWeatherApiCurrentUseCase = GetWeatherApiCurrentUseCase(
                weatherApiRepository = weatherApiRepository,
            ),
            getWeatherApiForecastUseCase = GetWeatherApiForecastUseCase(
                weatherApiRepository = weatherApiRepository,
            ),
            getWeatherApiAstronomyUseCase = GetWeatherApiAstronomyUseCase(
                weatherApiRepository = weatherApiRepository,
            ),
            getHourlyUseCase = GetHourlyUseCase(owmRepository = owmRepository),
        )
    }
}
