package ru.plumsoftware.weatherforecast.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import ru.plumsoftware.data.BuildConfig
import ru.plumsoftware.weatherforecast.domain.remote.dto.either.WeatherEither
import ru.plumsoftware.weatherforecast.domain.repository.WeatherApiRepository
import ru.plumsoftware.weatherforecast.domain.storage.SharedPreferencesStorage
import java.util.Locale

class WeatherApiRepositoryImpl(
    private val client: HttpClient,
    private val sharedPreferencesStorage: SharedPreferencesStorage
) : WeatherApiRepository {
    override suspend fun <D, E, R> getWeatherApi(): WeatherEither<D, E, R> {
        val response = client.get {
            url(urlString = "https://api.weatherapi.com/v1/forecast.json?key=${BuildConfig.WEATHER_API}")
            parameter(key = "q", value = "${sharedPreferencesStorage.get().city}")
            parameter(key = "days", value = "3")
            parameter(key = "aqi", value = "yes")
            parameter(key = "alerts", value = "yes")
            parameter(key = "lang", value = Locale.getDefault().language)
        }

        return try {
            val httpError = response.status as E
            val httpResponse = response.body<String>() as D
            val httpResponseTime = response.responseTime as R
            return WeatherEither(
                data = httpResponse,
                httpStatusCode = httpError,
                responseTime = httpResponseTime
            )
        } catch (e: RedirectResponseException) {
//            3xx
            val httpResponse = null as D
            val httpError = e as E
            val httpResponseTime = response.responseTime as R
            return WeatherEither(
                data = httpResponse,
                httpStatusCode = httpError,
                responseTime = httpResponseTime
            )
        } catch (e: ClientRequestException) {
//            4xx
            val httpResponse = null as D
            val httpError = e as E
            val httpResponseTime = response.responseTime as R
            return WeatherEither(
                data = httpResponse,
                httpStatusCode = httpError,
                responseTime = httpResponseTime
            )
        } catch (e: ServerResponseException) {
//            5xx
            val httpResponse = null as D
            val httpError = e as E
            val httpResponseTime = response.responseTime as R
            return WeatherEither(
                data = httpResponse,
                httpStatusCode = httpError,
                responseTime = httpResponseTime
            )
        } catch (e: Exception) {
            val httpResponse = null as D
            val httpError = e as E
            val httpResponseTime = response.responseTime as R
            return WeatherEither(
                data = httpResponse,
                httpStatusCode = httpError,
                responseTime = httpResponseTime
            )
        }
    }
}