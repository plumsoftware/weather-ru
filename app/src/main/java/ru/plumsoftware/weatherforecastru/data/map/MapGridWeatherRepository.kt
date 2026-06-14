package ru.plumsoftware.weatherforecastru.data.map

import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.osmdroid.util.GeoPoint
import ru.plumsoftware.weatherforecast.BuildConfig
import ru.plumsoftware.weatherforecastru.data.remote.dto.owm.OwmResponse
import kotlin.math.roundToInt

class MapGridWeatherRepository(
    private val httpClient: HttpClient,
) {
    private val gson = Gson()

    suspend fun loadGridLabels(
        centerLatitude: Double,
        centerLongitude: Double,
        mapLayer: WeatherMapLayer,
        units: String,
        windUnitLabel: String,
    ): List<WeatherGridLabel> {
        val gridPoints = MapGridCalculator.buildGridPoints(centerLatitude, centerLongitude)
        if (gridPoints.isEmpty()) return emptyList()

        val weatherByPoint = coroutineScope {
            gridPoints.map { point ->
                async {
                    point to fetchPointWeather(
                        latitude = point.latitude,
                        longitude = point.longitude,
                        units = units,
                    )
                }
            }.awaitAll()
        }

        return weatherByPoint.mapNotNull { (point, weather) ->
            weather?.toLabel(
                point = point,
                mapLayer = mapLayer,
                windUnitLabel = windUnitLabel,
            )
        }
    }

    private suspend fun fetchPointWeather(
        latitude: Double,
        longitude: Double,
        units: String,
    ): MapPointWeather? {
        val response = runCatching {
            httpClient.get("https://api.openweathermap.org/data/2.5/weather") {
                parameter("lat", latitude)
                parameter("lon", longitude)
                parameter("appid", BuildConfig.OWM_API_KEY)
                parameter("units", units)
            }
        }.getOrNull() ?: return null

        if (!response.status.isSuccess()) return null

        val body = runCatching { response.bodyAsText() }.getOrNull().orEmpty()
        val parsed = runCatching { gson.fromJson(body, OwmResponse::class.java) }.getOrNull() ?: return null

        return MapPointWeather(
            temperatureC = parsed.main?.temp?.roundToInt(),
            cloudinessPercent = parsed.clouds?.all,
            windSpeed = parsed.wind?.speed,
        )
    }

    private fun MapPointWeather.toLabel(
        point: GeoPoint,
        mapLayer: WeatherMapLayer,
        windUnitLabel: String,
    ): WeatherGridLabel? {
        val text = when (mapLayer) {
            WeatherMapLayer.Temperature -> {
                val value = temperatureC ?: return null
                "$value°"
            }
            WeatherMapLayer.CloudsWind -> {
                val clouds = cloudinessPercent ?: return null
                val wind = windSpeed ?: return null
                val windValue = if (wind % 1.0 == 0.0) {
                    wind.roundToInt().toString()
                } else {
                    String.format(java.util.Locale.US, "%.1f", wind)
                }
                "$clouds% · $windValue $windUnitLabel"
            }
        }

        return WeatherGridLabel(
            latitude = point.latitude,
            longitude = point.longitude,
            text = text,
        )
    }
}
