package ru.plumsoftware.weatherforecastru.data.repository

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import ru.plumsoftware.data.BuildConfig
import ru.plumsoftware.weatherforecastru.domain.remote.dto.either.WeatherEither
import ru.plumsoftware.weatherforecastru.domain.repository.OwmRepository
import ru.plumsoftware.weatherforecastru.domain.storage.SharedPreferencesStorage
import java.util.Locale

@Suppress("UNCHECKED_CAST")
class OwmRepositoryImpl(
    private val client: HttpClient,
    private val sharedPreferencesStorage: SharedPreferencesStorage
) : OwmRepository {
    override suspend fun <D, E, R> getOwm(): WeatherEither<D, E, R> {

        val response = client.get {
            url(urlString = "https://api.openweathermap.org/data/2.5/weather?q=${sharedPreferencesStorage.get().city}")
            parameter(key = "appid", value = BuildConfig.OWM_API_KEY)
            parameter(key = "lang", value = Locale.getDefault().language)
            parameter(key = "units", value = sharedPreferencesStorage.get().weatherUnits.unitsValue)
        }

        try {
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