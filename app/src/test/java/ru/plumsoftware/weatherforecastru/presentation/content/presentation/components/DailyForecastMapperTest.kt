package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.plumsoftware.weatherforecastru.data.constants.Constants
import ru.plumsoftware.weatherapp.weatherdata.forecast_owm.Clouds
import ru.plumsoftware.weatherapp.weatherdata.forecast_owm.MainInfo
import ru.plumsoftware.weatherapp.weatherdata.forecast_owm.Sys
import ru.plumsoftware.weatherapp.weatherdata.forecast_owm.WeatherDescription
import ru.plumsoftware.weatherapp.weatherdata.forecast_owm.WeatherItem
import ru.plumsoftware.weatherapp.weatherdata.forecast_owm.Wind
import ru.plumsoftware.weatherforecastru.data.remote.dto.forecast_owm.City
import ru.plumsoftware.weatherforecastru.data.remote.dto.forecast_owm.MainWeatherResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Condition
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Day
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Forecast
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Forecastday
import java.time.LocalDate

class DailyForecastMapperTest {

    @Test
    fun mapWeatherApiDailyItems_returnsSevenDaysWithPrecipitation() {
        val forecast = Forecast(
            forecastday = arrayListOf(
                forecastDay(date = "2026-06-12", min = 10, max = 20, rain = 18, code = 1003),
                forecastDay(date = "2026-06-13", min = 12, max = 22, rain = 0, code = 1000),
            ),
        )

        val items = mapWeatherApiDailyItems(
            forecast = forecast,
            unitsValue = Constants.Settings.METRIC.first,
            referenceLocalTime = "2026-06-12 12:00",
        )

        assertEquals(2, items.size)
        assertEquals(LocalDate.parse("2026-06-12"), items.first().date)
        assertEquals(18, items.first().precipChance)
        assertEquals(20, items.first().maxTemp)
    }

    @Test
    fun mapOwmDailyItems_aggregatesDailyMinMaxAndPop() {
        val response = MainWeatherResponse(
            weatherList = listOf(
                weatherItem("2026-06-12 09:00:00", temp = 15.0, tempMin = 14.0, tempMax = 16.0, pop = 0.1, id = 801),
                weatherItem("2026-06-12 12:00:00", temp = 18.0, tempMin = 17.0, tempMax = 19.0, pop = 0.4, id = 500),
                weatherItem("2026-06-12 15:00:00", temp = 20.0, tempMin = 19.0, tempMax = 21.0, pop = 0.2, id = 500),
            ),
            city = City(name = "Omsk"),
        )

        val items = mapOwmDailyItems(
            hourlyResponse = response,
            unitsValue = Constants.Settings.METRIC.first,
            owmHourlyCode = 200,
        )

        assertEquals(1, items.size)
        assertEquals(14, items.first().minTemp)
        assertEquals(21, items.first().maxTemp)
        assertEquals(40, items.first().precipChance)
        assertEquals("03d", items.first().iconCode)
    }

    @Test
    fun mergeDailyForecastItems_supplementsMissingDaysFromFallback() {
        val today = LocalDate.parse("2026-06-13")
        val weatherApiItems = listOf(
            dailyItem(today, 10, 20),
            dailyItem(today.plusDays(1), 12, 22),
            dailyItem(today.plusDays(2), 11, 21),
        )
        val owmItems = (0..6).map { offset ->
            dailyItem(today.plusDays(offset.toLong()), 9 + offset, 19 + offset)
        }

        val merged = mergeDailyForecastItems(
            primary = weatherApiItems,
            fallback = owmItems,
            maxDays = 7,
        )

        assertEquals(7, merged.size)
        assertEquals(today, merged.first().date)
        assertEquals(today.plusDays(6), merged.last().date)
    }

    private fun dailyItem(date: LocalDate, min: Int, max: Int) = DailyForecastUiItem(
        date = date,
        minTemp = min,
        maxTemp = max,
        precipChance = 10,
        iconCode = "01d",
    )

    private fun forecastDay(
        date: String,
        min: Int,
        max: Int,
        rain: Int,
        code: Int,
    ) = Forecastday(
        date = date,
        day = Day(
            mintempC = min.toDouble(),
            maxtempC = max.toDouble(),
            dailyChanceOfRain = rain.toDouble(),
            condition = Condition(code = code, text = "Cloudy"),
        ),
    )

    private fun weatherItem(
        dtTxt: String,
        temp: Double,
        tempMin: Double,
        tempMax: Double,
        pop: Double,
        id: Int,
    ) = WeatherItem(
        dt = 0,
        main = MainInfo(
            temp = temp,
            tempMin = tempMin,
            tempMax = tempMax,
            feelsLike = temp,
            pressure = 1000,
            seaLevel = 1000,
            grndLevel = 1000,
            humidity = 50,
            tempKf = 0.0,
        ),
        weather = listOf(WeatherDescription(id = id, main = "Clouds", description = "cloudy", icon = "03d")),
        clouds = Clouds(all = 50),
        wind = Wind(speed = 3.0, deg = 180, gust = 4.0),
        visibility = 10000,
        pop = pop,
        sys = Sys(pod = "d"),
        dtTxt = dtTxt,
    )
}
