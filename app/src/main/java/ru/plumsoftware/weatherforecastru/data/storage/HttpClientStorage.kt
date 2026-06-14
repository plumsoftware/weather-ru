package ru.plumsoftware.weatherforecastru.data.storage

import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetHourlyUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetOwmUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetWeatherApiAstronomyUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetWeatherApiCurrentUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetWeatherApiForecastUseCase

class HttpClientStorage(
    private val getOwmUseCase: GetOwmUseCase,
    private val getWeatherApiCurrentUseCase: GetWeatherApiCurrentUseCase,
    private val getWeatherApiForecastUseCase: GetWeatherApiForecastUseCase,
    private val getWeatherApiAstronomyUseCase: GetWeatherApiAstronomyUseCase,
    private val getHourlyUseCase: GetHourlyUseCase,
) {
    suspend fun <D, E, R> get(): WeatherEither<D, E, R> = getOwmUseCase.execute()

    suspend fun <D, E, R> getHourly(): WeatherEither<D, E, R> = getHourlyUseCase.execute()

    suspend fun <D, E, R> getWeatherApiCurrent(): WeatherEither<D, E, R> =
        getWeatherApiCurrentUseCase.execute()

    suspend fun <D, E, R> getWeatherApiForecast(): WeatherEither<D, E, R> =
        getWeatherApiForecastUseCase.execute()

    suspend fun <D, E, R> getWeatherApiAstronomy(date: String): WeatherEither<D, E, R> =
        getWeatherApiAstronomyUseCase.execute(date = date)

    suspend fun <D, E, R> getWeatherApi(): WeatherEither<D, E, R> =
        getWeatherApiCurrentUseCase.execute()
}
