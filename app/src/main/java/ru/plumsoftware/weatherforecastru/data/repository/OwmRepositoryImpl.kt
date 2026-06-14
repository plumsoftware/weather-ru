package ru.plumsoftware.weatherforecastru.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.util.date.GMTDate
import ru.plumsoftware.weatherforecast.BuildConfig
import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither
import ru.plumsoftware.weatherforecastru.data.utilities.logd

@Suppress("UNCHECKED_CAST")
class OwmRepositoryImpl(
    private val client: HttpClient,
    private val sharedPreferencesStorage: ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage,
) : OwmRepository {

    override suspend fun <D, E, R> getOwm(): WeatherEither<D, E, R> =
        executeRequest(url = "https://api.openweathermap.org/data/2.5/weather")

    override suspend fun <D, E, R> getOwmHourly(): WeatherEither<D, E, R> =
        executeRequest(url = "https://api.openweathermap.org/data/2.5/forecast")

    private fun locationQuery(): String =
        sharedPreferencesStorage.get().city?.trim().orEmpty()

    private suspend fun <D, E, R> executeRequest(url: String): WeatherEither<D, E, R> {
        val query = locationQuery()
        if (query.isBlank()) {
            logd("OWM request skipped: city is empty")
            return networkErrorEither(cause = IllegalStateException("city is empty"))
        }

        val response = try {
            client.get(urlString = url) {
                parameter(key = "q", value = query)
                parameter(key = "appid", value = BuildConfig.OWM_API_KEY)
                parameter(key = "lang", value = "ru")
                parameter(key = "units", value = sharedPreferencesStorage.get().weatherUnits.unitsValue)
            }
        } catch (e: Exception) {
            return networkErrorEither(cause = e)
        }

        return readEither(response = response)
    }

    private suspend fun <D, E, R> readEither(response: HttpResponse): WeatherEither<D, E, R> {
        return try {
            WeatherEither(
                data = response.bodyAsText() as D,
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
            httpErrorEither(response = response, cause = e)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun <D, E, R> httpErrorEither(
        response: HttpResponse,
        cause: Exception,
    ): WeatherEither<D, E, R> {
        logd("OWM HTTP ${response.status.value}: ${cause.message}")
        return WeatherEither(
            data = runCatching { response.bodyAsText() }.getOrDefault("") as D,
            httpStatusCode = response.status as E,
            responseTime = response.responseTime as R,
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun <D, E, R> networkErrorEither(cause: Exception): WeatherEither<D, E, R> {
        logd("OWM network error: ${cause.javaClass.simpleName}: ${cause.message}")
        return WeatherEither(
            data = "" as D,
            httpStatusCode = HttpStatusCode(0, "Network Error") as E,
            responseTime = GMTDate() as R,
        )
    }
}
