package ru.plumsoftware.weatherforecastru.data.usecase.weather

import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither
import ru.plumsoftware.weatherforecastru.data.repository.WeatherApiRepository

class GetWeatherApiAstronomyUseCase(
    private val weatherApiRepository: WeatherApiRepository,
) {
    suspend fun <D, E, R> execute(date: String): WeatherEither<D, E, R> =
        weatherApiRepository.getAstronomy(date = date)
}
