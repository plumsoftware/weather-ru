package ru.plumsoftware.weatherforecastru.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.util.date.GMTDate
import ru.plumsoftware.weatherforecast.BuildConfig
import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither
import ru.plumsoftware.weatherforecastru.data.utilities.logd

@Suppress("UNCHECKED_CAST")
class WeatherApiRepositoryImpl(
    private val client: HttpClient,
    private val sharedPreferencesStorage: ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage,
) : WeatherApiRepository {

    override suspend fun <D, E, R> getCurrent(): WeatherEither<D, E, R> =
        executeRequest(endpoint = "https://api.weatherapi.com/v1/current.json") {
            parameter(key = "aqi", value = "yes")
        }

    override suspend fun <D, E, R> getForecast(): WeatherEither<D, E, R> =
        executeRequest(endpoint = "https://api.weatherapi.com/v1/forecast.json") {
            parameter(key = "days", value = FORECAST_DAYS)
            parameter(key = "aqi", value = "no")
            parameter(key = "alerts", value = "yes")
        }

    override suspend fun <D, E, R> search(query: String): WeatherEither<D, E, R> =
        executeSearchRequest(query = query)

    override suspend fun <D, E, R> getAstronomy(date: String): WeatherEither<D, E, R> =
        executeRequest(endpoint = "https://api.weatherapi.com/v1/astronomy.json") {
            parameter(key = "dt", value = date)
        }

    private fun locationQuery(): String =
        sharedPreferencesStorage.get().city?.trim().orEmpty().ifBlank { DEFAULT_QUERY }

    private suspend fun <D, E, R> executeSearchRequest(
        query: String,
    ): WeatherEither<D, E, R> {
        val response = try {
            client.get(urlString = "https://api.weatherapi.com/v1/search.json") {
                parameter(key = "key", value = BuildConfig.WEATHER_API)
                parameter(key = "q", value = query)
                parameter(key = "lang", value = API_LANG)
            }
        } catch (e: Exception) {
            return networkErrorEither(cause = e, endpoint = "search.json")
        }

        return readEither(response = response)
    }

    private suspend fun <D, E, R> executeRequest(
        endpoint: String,
        extraParams: HttpRequestBuilder.() -> Unit = {},
    ): WeatherEither<D, E, R> {
        val query = locationQuery()
        val response = try {
            client.get(urlString = endpoint) {
                parameter(key = "key", value = BuildConfig.WEATHER_API)
                parameter(key = "q", value = query)
                parameter(key = "lang", value = API_LANG)
                extraParams()
            }
        } catch (e: Exception) {
            return networkErrorEither(cause = e, endpoint = endpoint)
        }

        return readEither(response = response)
    }

    private suspend fun <D, E, R> readEither(
        response: HttpResponse,
    ): WeatherEither<D, E, R> {
        return try {
            val body = response.bodyAsText()
            WeatherEither(
                data = body as D,
                httpStatusCode = response.status as E,
                responseTime = response.responseTime as R,
            )
        } catch (e: RedirectResponseException) {
            httpErrorEither(response = e.response, cause = e)
        } catch (e: ClientRequestException) {
            httpErrorEither(response = e.response, cause = e)
        } catch (e: ServerResponseException) {
            httpErrorEither(response = e.response, cause = e)
        } catch (e: Exception) {
            if (response.status.isSuccess()) {
                networkErrorEither(cause = e, endpoint = response.call.request.url.toString())
            } else {
                httpErrorEither(response = response, cause = e)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun <D, E, R> httpErrorEither(
        response: HttpResponse,
        cause: Exception,
    ): WeatherEither<D, E, R> {
        logd("WeatherAPI HTTP ${response.status.value}: ${cause.message}")
        return WeatherEither(
            data = runCatching { response.bodyAsText() }.getOrDefault("") as D,
            httpStatusCode = response.status as E,
            responseTime = response.responseTime as R,
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun <D, E, R> networkErrorEither(
        cause: Exception,
        endpoint: String,
    ): WeatherEither<D, E, R> {
        logd("WeatherAPI network error ($endpoint): ${cause.javaClass.simpleName}: ${cause.message}")
        return WeatherEither(
            data = "" as D,
            httpStatusCode = HttpStatusCode(0, "Network Error") as E,
            responseTime = GMTDate() as R,
        )
    }

    private companion object {
        const val FORECAST_DAYS = "7"
        const val DEFAULT_QUERY = "auto:ip"
        const val API_LANG = "ru"
    }
}
