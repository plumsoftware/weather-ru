package ru.plumsoftware.weatherforecastru.data.map

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.http.HttpStatusCode
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiSearchItem
import ru.plumsoftware.weatherforecastru.data.repository.WeatherApiRepository

class MapLocationResolver(
    private val weatherApiRepository: WeatherApiRepository,
) {
    suspend fun resolve(
        city: String,
        fallbackLatitude: Double,
        fallbackLongitude: Double,
    ): Pair<Double, Double> {
        if (city.isBlank()) {
            return fallbackLatitude to fallbackLongitude
        }
        val either = weatherApiRepository.search<String, HttpStatusCode, Any>(query = city)
        val statusCode = either.httpStatusCode as? HttpStatusCode ?: return fallbackLatitude to fallbackLongitude
        if (statusCode.value !in 200..299) {
            return fallbackLatitude to fallbackLongitude
        }
        val items = runCatching {
            Gson().fromJson<List<WeatherApiSearchItem>>(
                either.data,
                object : TypeToken<List<WeatherApiSearchItem>>() {}.type,
            )
        }.getOrNull().orEmpty()
        val match = items.firstOrNull { item ->
            item.lat != null && item.lon != null
        } ?: return fallbackLatitude to fallbackLongitude
        return match.lat!! to match.lon!!
    }
}
