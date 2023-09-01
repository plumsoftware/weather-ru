package ru.plumsoftware.weatherforecast.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import ru.plumsoftware.weatherforecast.domain.remote.dto.either.OwmEither
import ru.plumsoftware.weatherforecast.domain.repository.OwmRepository
import ru.plumsoftware.weatherforecast.domain.storage.SharedPreferencesStorage
import java.util.Locale

class OwmRepositoryImpl(
    private val client: HttpClient,
    private val sharedPreferencesStorage: SharedPreferencesStorage
) : OwmRepository {
    override suspend fun <D, E, R> getOwm(): OwmEither<D, E, R> {

        val response = client.get {
            url(urlString = "https://api.openweathermap.org/data/2.5/weather?q=${sharedPreferencesStorage.get().city}")
            parameter(key = "appid", value = "4e228e1be370d9d0d02284441d30cf0b") // TODO(replace with local.properties)
            parameter(key = "lang", value = Locale.getDefault().language)
            parameter(key = "units", value = sharedPreferencesStorage.get().weatherUnits.unitsValue)
        }

        return try {
            val httpError = response.status as E
            val httpResponse = response.body<String>() as D
            val httpResponseTime = response.responseTime as R
            return OwmEither(
                data = httpResponse,
                httpStatusCode = httpError,
                responseTime = httpResponseTime
            )
        } catch (e: RedirectResponseException) {
//            3xx
            val httpResponse = null as D
            val httpError = e as E
            val httpResponseTime = response.responseTime as R
            return OwmEither(
                data = httpResponse,
                httpStatusCode = httpError,
                responseTime = httpResponseTime
            )
        } catch (e: ClientRequestException) {
//            4xx
            val httpResponse = null as D
            val httpError = e as E
            val httpResponseTime = response.responseTime as R
            return OwmEither(
                data = httpResponse,
                httpStatusCode = httpError,
                responseTime = httpResponseTime
            )
        } catch (e: ServerResponseException) {
//            5xx
            val httpResponse = null as D
            val httpError = e as E
            val httpResponseTime = response.responseTime as R
            return OwmEither(
                data = httpResponse,
                httpStatusCode = httpError,
                responseTime = httpResponseTime
            )
        } catch (e: Exception) {
            val httpResponse = null as D
            val httpError = e as E
            val httpResponseTime = response.responseTime as R
            return OwmEither(
                data = httpResponse,
                httpStatusCode = httpError,
                responseTime = httpResponseTime
            )
        }
    }
//    override suspend fun getOwm() : OwmResponse {
//        return try {
//            val response = client.get {
//                url(urlString = "https://api.openweathermap.org/data/2.5/weather?q=Omsk&appid=4e228e1be370d9d0d02284441d30cf0b")
//            }
//            val httpError = response.status
//            val httpResponse = response.body<OwmResponse>()
////            val httpResponse = response as D
//            return httpResponse
//        } catch (e: RedirectResponseException) {
////            3xx
//            val httpResponse = null
//            val httpError = e
//            return OwmResponse(
//                cod = 300
//            )
//        } catch (e: ClientRequestException) {
////            4xx
//            val httpResponse = null
//            val httpError = e
//            return OwmResponse(
//                cod = 400
//            )
//        } catch (e: ServerResponseException) {
////            5xx
//            val httpResponse = null
//            val httpError = e
//            return OwmResponse(
//                cod = 500
//            )
//        } catch (e: Exception) {
//            val httpResponse = null
//            val httpError = e
//            return OwmResponse(
//                cod = -1
//            )
//        }
//    }
}