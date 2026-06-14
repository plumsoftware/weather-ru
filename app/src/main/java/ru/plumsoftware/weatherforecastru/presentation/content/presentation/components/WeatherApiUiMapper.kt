package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import ru.plumsoftware.weatherforecastru.data.constants.Constants
import ru.plumsoftware.weatherapp.weatherdata.forecast_owm.WeatherItem
import ru.plumsoftware.weatherforecastru.data.remote.dto.forecast_owm.MainWeatherResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Astro
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Current
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Day
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Forecast
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Forecastday
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Hour
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiResponse
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.Calendar
import java.util.Date
import java.util.Locale

import ru.plumsoftware.weatherforecastru.data.weather.WeatherIconCodes

fun weatherApiConditionToOwmIcon(code: Int): Int = when (code) {
    1000 -> 800
    1003 -> 801
    1006 -> 803
    1009 -> 804
    1030, 1135, 1147 -> 741
    1063, 1150, 1153, 1168, 1171, 1180, 1183, 1186, 1189, 1192, 1195, 1198, 1201 -> 500
    1087, 1273, 1276, 1279 -> 200
    1066, 1210, 1213, 1216, 1219, 1222, 1225 -> 600
    1114, 1117 -> 602
    1237, 1240, 1243, 1246, 1249, 1252, 1255, 1258, 1261, 1264 -> 500
    else -> 803
}

fun WeatherApiResponse.hasCurrentWeather(): Boolean =
    current?.condition?.text.orEmpty().isNotEmpty()

fun weatherApiTemperature(
    current: Current?,
    day: Day?,
    unitsValue: String,
): WeatherApiTemperatures {
    val isImperial = unitsValue == Constants.Settings.IMPERIAL.first
    return WeatherApiTemperatures(
        current = if (isImperial) current?.tempF?.toInt() else current?.tempC?.toInt(),
        feelsLike = if (isImperial) current?.feelslikeF?.toInt() else current?.feelslikeC?.toInt(),
        high = if (isImperial) day?.maxtempF?.toInt() else day?.maxtempC?.toInt(),
        low = if (isImperial) day?.mintempF?.toInt() else day?.mintempC?.toInt(),
    )
}

data class WeatherApiTemperatures(
    val current: Int?,
    val feelsLike: Int?,
    val high: Int?,
    val low: Int?,
)

fun weatherApiWindSpeed(current: Current?, unitsValue: String): Int {
    val isImperial = unitsValue == Constants.Settings.IMPERIAL.first
    return if (isImperial) {
        current?.windMph?.toInt() ?: 0
    } else {
        ((current?.windKph ?: 0.0) / 3.6).toInt()
    }
}

private data class ParsedHour(
    val dateTime: LocalDateTime,
    val hour: Hour,
)

private val forecastDateTimeFormatter: DateTimeFormatter = DateTimeFormatterBuilder()
    .appendPattern("yyyy-MM-dd ")
    .appendValue(ChronoField.HOUR_OF_DAY, 1, 2, java.time.format.SignStyle.NOT_NEGATIVE)
    .appendPattern(":mm")
    .toFormatter()

private fun parseForecastHours(forecast: Forecast?): List<ParsedHour> {
    return forecast?.forecastday.orEmpty()
        .flatMap { forecastDay ->
            forecastDay.hour.mapNotNull { hour ->
                val time = hour.time?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
                val dateTime = runCatching { LocalDateTime.parse(time, forecastDateTimeFormatter) }.getOrNull()
                    ?: return@mapNotNull null
                ParsedHour(dateTime = dateTime, hour = hour)
            }
        }
}

fun weatherApiForecastHasHourlyData(forecast: Forecast?): Boolean =
    parseForecastHours(forecast).isNotEmpty()

private fun findCurrentParsedHour(
    parsedHours: List<ParsedHour>,
    referenceLocalTime: String?,
): ParsedHour? {
    if (parsedHours.isEmpty()) return null
    val now = parseReferenceLocalTime(referenceLocalTime)
    return parsedHours.firstOrNull { parsed ->
        parsed.dateTime.toLocalDate() == now.toLocalDate() && parsed.dateTime.hour == now.hour
    } ?: parsedHours.firstOrNull { it.dateTime >= now.withMinute(0).withSecond(0) }
}

private fun Hour.toCurrent(lastUpdated: String): Current = Current(
    lastUpdated = lastUpdated,
    tempC = tempC,
    tempF = tempF,
    isDay = isDay,
    condition = condition,
    windMph = windMph,
    windKph = windKph,
    windDegree = windDegree,
    windDir = windDir,
    pressureMb = pressureMb,
    pressureIn = pressureIn,
    precipMm = precipMm,
    precipIn = precipIn,
    humidity = humidity,
    cloud = cloud?.toInt(),
    feelslikeC = feelslikeC,
    feelslikeF = feelslikeF,
    visKm = visKm,
    visMiles = visMiles,
    uv = uv,
    gustMph = gustMph,
    gustKph = gustKph,
)

fun resolveWeatherApiCurrent(response: WeatherApiResponse): Current? {
    response.current?.takeIf { it.condition?.text.orEmpty().isNotEmpty() }?.let { return it }

    val referenceLocalTime = response.location?.localtime ?: return null
    val currentHour = findCurrentParsedHour(
        parsedHours = parseForecastHours(response.forecast),
        referenceLocalTime = referenceLocalTime,
    ) ?: return null

    return currentHour.hour.toCurrent(lastUpdated = referenceLocalTime)
}

private fun parseReferenceLocalTime(referenceLocalTime: String?): LocalDateTime {
    if (referenceLocalTime.isNullOrBlank()) return LocalDateTime.now()
    return runCatching { LocalDateTime.parse(referenceLocalTime, forecastDateTimeFormatter) }
        .getOrDefault(LocalDateTime.now())
}

fun mapWeatherApiHourlyItems(
    forecast: Forecast?,
    unitsValue: String,
    referenceLocalTime: String? = null,
): List<HourlyForecastEntry> {
    val parsedHours = parseForecastHours(forecast)
    if (parsedHours.isEmpty()) return emptyList()

    val now = parseReferenceLocalTime(referenceLocalTime)
    val startIndex = parsedHours.indexOfFirst { parsed ->
        parsed.dateTime.toLocalDate() == now.toLocalDate() && parsed.dateTime.hour == now.hour
    }.let { exactIndex ->
        when {
            exactIndex >= 0 -> exactIndex
            else -> parsedHours.indexOfFirst { it.dateTime >= now.withMinute(0).withSecond(0) }
                .coerceAtLeast(0)
        }
    }

    val window = parsedHours.drop(startIndex).take(24).ifEmpty {
        parsedHours.take(24)
    }
    if (window.isEmpty()) return emptyList()

    val isImperial = unitsValue == Constants.Settings.IMPERIAL.first

    return buildList {
        window.forEachIndexed { index, parsed ->
            if (index > 0 &&
                parsed.dateTime.toLocalDate() != window[index - 1].dateTime.toLocalDate()
            ) {
                add(HourlyForecastEntry.DayDivider)
            }
            val temp = if (isImperial) {
                parsed.hour.tempF?.toInt() ?: 0
            } else {
                parsed.hour.tempC?.toInt() ?: 0
            }
            add(
                HourlyForecastEntry.Hour(
                    data = HourlyUiItem(
                        time = parsed.dateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        temp = temp,
                        precipChance = parsed.hour.chanceOfRain ?: 0,
                        iconCode = WeatherIconCodes.fromWeatherApiCode(
                            parsed.hour.condition?.code ?: 1000,
                            isDay = parsed.hour.isDay == 1,
                        ),
                        isNow = parsed.dateTime.toLocalDate() == now.toLocalDate() &&
                            parsed.dateTime.hour == now.hour,
                    ),
                ),
            )
        }
    }
}

fun mapOwmHourlyItems(
    hourlyResponse: MainWeatherResponse,
    unitsValue: String,
    owmHourlyCode: Int,
): List<HourlyForecastEntry> {
    if (owmHourlyCode in 300..599 && hourlyResponse.weatherList.isEmpty()) return emptyList()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val parsedHours = hourlyResponse.weatherList.mapNotNull { item ->
        val dateTime = runCatching { LocalDateTime.parse(item.dtTxt, formatter) }.getOrNull()
            ?: return@mapNotNull null
        ParsedOwmHour(dateTime = dateTime, item = item)
    }
    if (parsedHours.isEmpty()) return emptyList()

    val now = LocalDateTime.now()
    val startIndex = parsedHours.indexOfFirst { parsed ->
        parsed.dateTime.toLocalDate() == now.toLocalDate() && parsed.dateTime.hour == now.hour
    }.let { exactIndex ->
        when {
            exactIndex >= 0 -> exactIndex
            else -> parsedHours.indexOfFirst { it.dateTime >= now.withMinute(0).withSecond(0) }
                .coerceAtLeast(0)
        }
    }

    val window = parsedHours.drop(startIndex).take(24).ifEmpty {
        parsedHours.take(24)
    }
    if (window.isEmpty()) return emptyList()

    return buildList {
        window.forEachIndexed { index, parsed ->
            if (index > 0 &&
                parsed.dateTime.toLocalDate() != window[index - 1].dateTime.toLocalDate()
            ) {
                add(HourlyForecastEntry.DayDivider)
            }
            val temp = parsed.item.main.temp.toInt()
            add(
                HourlyForecastEntry.Hour(
                    data = HourlyUiItem(
                        time = parsed.dateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        temp = temp,
                        precipChance = (parsed.item.pop * 100).toInt(),
                        iconCode = WeatherIconCodes.fromOwmIcon(
                            parsed.item.weather.firstOrNull()?.icon,
                        ),
                        isNow = parsed.dateTime.toLocalDate() == now.toLocalDate() &&
                            parsed.dateTime.hour == now.hour,
                    ),
                ),
            )
        }
    }
}

private data class ParsedOwmHour(
    val dateTime: LocalDateTime,
    val item: WeatherItem,
)

fun parseWeatherApiSunTime(time: String?): Long? {
    if (time.isNullOrBlank()) return null
    return runCatching {
        val parsed = SimpleDateFormat("hh:mm a", Locale.US).parse(time) ?: return null
        val source = Calendar.getInstance().apply { this.time = parsed }
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, source.get(Calendar.HOUR_OF_DAY))
            set(Calendar.MINUTE, source.get(Calendar.MINUTE))
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }.getOrNull()
}

fun formatAstroTime(timeMillis: Long?): String {
    if (timeMillis == null) return "—"
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timeMillis))
}

private fun validAstroTime(value: String?): String? =
    value?.takeIf { it.isNotBlank() && it != "0" }

fun resolveSunriseSunsetTimes(
    useOwmForCurrent: Boolean,
    owmSunriseEpochSec: Int?,
    owmSunsetEpochSec: Int?,
    forecastAstro: Astro?,
    astronomyAstro: Astro?,
): Pair<Long?, Long?> {
    if (useOwmForCurrent) {
        val sunrise = owmSunriseEpochSec?.takeIf { it > 0 }?.times(1000L)
        val sunset = owmSunsetEpochSec?.takeIf { it > 0 }?.times(1000L)
        if (sunrise != null && sunset != null) return sunrise to sunset
    }

    parseWeatherApiSunTime(validAstroTime(forecastAstro?.sunrise))?.let { sunrise ->
        parseWeatherApiSunTime(validAstroTime(forecastAstro?.sunset))?.let { sunset ->
            return sunrise to sunset
        }
    }

    parseWeatherApiSunTime(validAstroTime(astronomyAstro?.sunrise))?.let { sunrise ->
        parseWeatherApiSunTime(validAstroTime(astronomyAstro?.sunset))?.let { sunset ->
            return sunrise to sunset
        }
    }

    return null to null
}

fun translateMoonPhase(languageTag: String, moonPhase: String): String {
    val moonPhaseMapping = mapOf(
        "Waning Crescent" to "Убывающая серповидная",
        "New Moon" to "Новолуние",
        "Waxing Crescent" to "Растущая серповидная",
        "First Quarter" to "Первая четверть",
        "Waxing Gibbous" to "Растущая выпуклая",
        "Full Moon" to "Полнолуние",
        "Waning Gibbous" to "Убывающая выпуклая",
        "Last Quarter" to "Последняя четверть",
    )
    return if (languageTag == "ru") {
        moonPhaseMapping[moonPhase] ?: moonPhase
    } else {
        moonPhase
    }
}

data class DailyForecastUiItem(
    val date: LocalDate,
    val minTemp: Int,
    val maxTemp: Int,
    val precipChance: Int,
    val iconCode: String,
)

fun formatDayLabel(
    date: LocalDate,
    today: LocalDate,
    todayLabel: String,
    tomorrowLabel: String,
    locale: Locale = Locale("ru", "RU"),
): String = when (date) {
    today -> todayLabel
    today.plusDays(1) -> tomorrowLabel
    else -> date.format(DateTimeFormatter.ofPattern("EE", locale))
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
}

fun mergeDailyForecastItems(
    primary: List<DailyForecastUiItem>,
    fallback: List<DailyForecastUiItem>,
    maxDays: Int = 7,
): List<DailyForecastUiItem> {
    if (primary.size >= maxDays) return primary.take(maxDays)
    val merged = primary.toMutableList()
    val seenDates = merged.map { it.date }.toMutableSet()
    fallback.sortedBy { it.date }.forEach { item ->
        if (merged.size >= maxDays) return@forEach
        if (seenDates.add(item.date)) {
            merged.add(item)
        }
    }
    return merged.sortedBy { it.date }
}

fun weatherApiForecastDayCount(forecast: Forecast?): Int =
    forecast?.forecastday.orEmpty().size

fun weatherApiReferenceDate(referenceLocalTime: String?): LocalDate =
    parseReferenceLocalTime(referenceLocalTime).toLocalDate()

fun mapWeatherApiDailyItems(
    forecast: Forecast?,
    unitsValue: String,
    referenceLocalTime: String?,
): List<DailyForecastUiItem> {
    val today = parseReferenceLocalTime(referenceLocalTime).toLocalDate()
    val isImperial = unitsValue == Constants.Settings.IMPERIAL.first
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    return forecast?.forecastday.orEmpty()
        .take(7)
        .mapNotNull { forecastDay -> mapForecastDay(forecastDay, today, isImperial, dateFormatter) }
}

private fun mapForecastDay(
    forecastDay: Forecastday,
    today: LocalDate,
    isImperial: Boolean,
    dateFormatter: DateTimeFormatter,
): DailyForecastUiItem? {
    val dateString = forecastDay.date?.takeIf { it.isNotBlank() } ?: return null
    val date = runCatching { LocalDate.parse(dateString, dateFormatter) }.getOrNull() ?: return null
    val day = forecastDay.day ?: return null
    val minTemp = (if (isImperial) day.mintempF?.toInt() else day.mintempC?.toInt()) ?: return null
    val maxTemp = (if (isImperial) day.maxtempF?.toInt() else day.maxtempC?.toInt()) ?: return null

    return DailyForecastUiItem(
        date = date,
        minTemp = minTemp,
        maxTemp = maxTemp,
        precipChance = day.dailyChanceOfRain?.toInt() ?: 0,
        iconCode = WeatherIconCodes.fromWeatherApiCode(
            day.condition?.code ?: 1000,
            isDay = true,
        ),
    )
}

fun mapOwmDailyItems(
    hourlyResponse: MainWeatherResponse,
    unitsValue: String,
    owmHourlyCode: Int,
): List<DailyForecastUiItem> {
    if (owmHourlyCode in 300..599 && hourlyResponse.weatherList.isEmpty()) return emptyList()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val grouped = hourlyResponse.weatherList.mapNotNull { item ->
        val dateTime = runCatching { LocalDateTime.parse(item.dtTxt, formatter) }.getOrNull()
            ?: return@mapNotNull null
        dateTime to item
    }.groupBy({ it.first.toLocalDate() }, { it.second })

    if (grouped.isEmpty()) return emptyList()

    return grouped.toSortedMap().entries.take(7).map { entry ->
        val date = entry.key
        val dayItems = entry.value
        val minTemp = dayItems.minOf { weatherItem -> weatherItem.main.tempMin.toInt() }
        val maxTemp = dayItems.maxOf { weatherItem -> weatherItem.main.tempMax.toInt() }
        val maxPop = dayItems.maxOf { weatherItem -> (weatherItem.pop * 100).toInt() }
        val middayItem = dayItems.minByOrNull { weatherItem ->
            val hour = runCatching {
                LocalDateTime.parse(weatherItem.dtTxt, formatter).hour
            }.getOrElse { 12 }
            kotlin.math.abs(hour - 12)
        } ?: dayItems.first()
        DailyForecastUiItem(
            date = date,
            minTemp = minTemp,
            maxTemp = maxTemp,
            precipChance = maxPop,
            iconCode = WeatherIconCodes.fromOwmIcon(
                middayItem.weather.firstOrNull()?.icon,
            ),
        )
    }
}
