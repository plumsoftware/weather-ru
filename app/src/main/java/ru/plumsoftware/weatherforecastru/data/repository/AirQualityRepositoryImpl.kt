package ru.plumsoftware.weatherforecastru.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import ru.plumsoftware.weatherforecastru.data.models.airquality.AirQualityData
import ru.plumsoftware.weatherforecastru.data.models.airquality.AqiForecastDay
import ru.plumsoftware.weatherforecastru.data.models.airquality.Pollutant
import ru.plumsoftware.weatherforecastru.data.models.airquality.aqiDescription
import ru.plumsoftware.weatherforecastru.data.models.airquality.aqiLabel
import ru.plumsoftware.weatherforecastru.data.remote.dto.openmeteo.OpenMeteoAirQualityResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AirQualityRepositoryImpl(
    private val client: HttpClient,
) : AirQualityRepository {

    override suspend fun getAirQuality(lat: Double, lon: Double): AirQualityData {
        val response: OpenMeteoAirQualityResponse = client.get(
            urlString = "https://air-quality-api.open-meteo.com/v1/air-quality"
        ) {
            parameter("latitude", lat)
            parameter("longitude", lon)
            parameter(
                "current",
                "european_aqi,pm10,pm2_5,carbon_monoxide,nitrogen_dioxide,ozone,sulphur_dioxide"
            )
            parameter("hourly", "european_aqi")
            parameter("timezone", "auto")
        }.body()

        val current = response.current
        val aqi = current?.europeanAqi ?: 0

        val pollutants = buildList {
            current?.pm25?.let {
                add(
                    Pollutant(
                        name = "PM2.5",
                        fullName = "Мелкие частицы",
                        value = it.toFloat(),
                        unit = "мкг/м³",
                        normalMax = 15f,
                    )
                )
            }
            current?.pm10?.let {
                add(
                    Pollutant(
                        name = "PM10",
                        fullName = "Крупные частицы",
                        value = it.toFloat(),
                        unit = "мкг/м³",
                        normalMax = 45f,
                    )
                )
            }
            current?.nitrogenDioxide?.let {
                add(
                    Pollutant(
                        name = "NO₂",
                        fullName = "Диоксид азота",
                        value = it.toFloat(),
                        unit = "мкг/м³",
                        normalMax = 25f,
                    )
                )
            }
            current?.ozone?.let {
                add(
                    Pollutant(
                        name = "O₃",
                        fullName = "Озон",
                        value = it.toFloat(),
                        unit = "мкг/м³",
                        normalMax = 100f,
                    )
                )
            }
            current?.carbonMonoxide?.let {
                add(
                    Pollutant(
                        name = "CO",
                        fullName = "Угарный газ",
                        value = it.toFloat(),
                        unit = "мкг/м³",
                        normalMax = 4400f,
                    )
                )
            }
            current?.sulphurDioxide?.let {
                add(
                    Pollutant(
                        name = "SO₂",
                        fullName = "Диоксид серы",
                        value = it.toFloat(),
                        unit = "мкг/м³",
                        normalMax = 40f,
                    )
                )
            }
        }

        val forecast = buildForecast(response)

        return AirQualityData(
            aqi = aqi,
            aqiLabel = aqiLabel(aqi),
            aqiDescription = aqiDescription(aqi),
            pollutants = pollutants,
            forecast = forecast,
        )
    }

    private fun buildForecast(response: OpenMeteoAirQualityResponse): List<AqiForecastDay> {
        val times = response.hourly?.time.orEmpty()
        val aqis = response.hourly?.europeanAqi.orEmpty()
        if (times.isEmpty() || aqis.isEmpty()) return emptyList()

        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val today = LocalDate.now()
        val labels = listOf("Сегодня", "Завтра", "Послезавтра")
        val result = mutableListOf<AqiForecastDay>()

        for (dayOffset in 0..2) {
            val targetDate = today.plusDays(dayOffset.toLong())
            var dayAqi = 0
            for (i in times.indices) {
                val dateTime = runCatching { java.time.LocalDateTime.parse(times[i], formatter) }.getOrNull()
                    ?: continue
                if (dateTime.toLocalDate() == targetDate && dateTime.hour == 12) {
                    dayAqi = aqis.getOrNull(i) ?: 0
                    break
                }
            }
            if (dayAqi == 0) {
                for (i in times.indices) {
                    val dateTime = runCatching { java.time.LocalDateTime.parse(times[i], formatter) }.getOrNull()
                        ?: continue
                    if (dateTime.toLocalDate() == targetDate) {
                        dayAqi = aqis.getOrNull(i) ?: dayAqi
                    }
                }
            }
            if (dayAqi > 0) {
                result.add(
                    AqiForecastDay(
                        label = labels.getOrElse(dayOffset) { targetDate.toString() },
                        aqi = dayAqi,
                        aqiLabel = aqiLabel(dayAqi),
                    )
                )
            }
        }
        return result
    }
}
