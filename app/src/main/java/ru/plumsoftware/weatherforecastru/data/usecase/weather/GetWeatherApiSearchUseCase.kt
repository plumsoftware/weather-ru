package ru.plumsoftware.weatherforecastru.data.usecase.weather

import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither
import ru.plumsoftware.weatherforecastru.data.repository.WeatherApiRepository

class GetWeatherApiSearchUseCase(
    private val weatherApiRepository: WeatherApiRepository,
) {
    suspend fun <D, E, R> execute(query: String): WeatherEither<D, E, R> =
        weatherApiRepository.search(query = query)
}
