package ru.plumsoftware.weatherforecastru.data.usecase.weather

import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither
import ru.plumsoftware.weatherforecastru.data.repository.WeatherApiRepository

class GetWeatherApiCurrentUseCase(
    private val weatherApiRepository: WeatherApiRepository,
) {
    suspend fun <D, E, R> execute(): WeatherEither<D, E, R> =
        weatherApiRepository.getCurrent()
}
