package ru.plumsoftware.weatherforecastru.presentation.app

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.plumsoftware.weatherforecastru.data.models.airquality.AirQualityData
import ru.plumsoftware.weatherforecastru.data.remote.dto.forecast_owm.MainWeatherResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Astro
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiResponse

data class WeatherSessionState(
    val owmResponse: OwmResponse = OwmResponse(),
    val owmHourlyResponse: MainWeatherResponse = MainWeatherResponse(),
    val weatherApiResponse: WeatherApiResponse = WeatherApiResponse(),
    val astronomyAstro: Astro? = null,
    val airQualityData: AirQualityData = AirQualityData(),
    val owmCode: Int = -1,
    val owmHourlyCode: Int = -1,
    val weatherApiCode: Int = -1,
    val isLoading: Boolean = false,
    val useOwmForCurrent: Boolean = false,
) {
    fun hasWeatherData(): Boolean =
        useOwmForCurrent && owmResponse.base.orEmpty().isNotEmpty() ||
            !useOwmForCurrent && weatherApiResponse.current?.condition?.text.orEmpty().isNotEmpty() ||
            weatherApiResponse.forecast?.forecastday.orEmpty().isNotEmpty()
}

class WeatherSession {
    private val _state = MutableStateFlow(WeatherSessionState())
    val state: StateFlow<WeatherSessionState> = _state.asStateFlow()

    private val _contentRefreshToken = MutableStateFlow(0)
    val contentRefreshToken: StateFlow<Int> = _contentRefreshToken.asStateFlow()

    private var fetchedForCurrentCity = false
    private var needsRefresh = false

    fun shouldFetchOnContentEnter(): Boolean = needsRefresh || !fetchedForCurrentCity

    fun markCityChanged() {
        fetchedForCurrentCity = false
        needsRefresh = true
        _contentRefreshToken.value += 1
    }

    fun markSettingsChanged() {
        needsRefresh = true
    }

    fun setLoading(loading: Boolean) {
        _state.update { it.copy(isLoading = loading) }
    }

    fun applyWeather(
        owmResponse: OwmResponse,
        owmHourlyResponse: MainWeatherResponse,
        weatherApiResponse: WeatherApiResponse,
        astronomyAstro: Astro?,
        airQualityData: AirQualityData,
        owmCode: Int,
        owmHourlyCode: Int,
        weatherApiCode: Int,
        useOwmForCurrent: Boolean,
    ) {
        fetchedForCurrentCity = true
        needsRefresh = false
        _state.update {
            it.copy(
                owmResponse = owmResponse,
                owmHourlyResponse = owmHourlyResponse,
                weatherApiResponse = weatherApiResponse,
                astronomyAstro = astronomyAstro,
                airQualityData = airQualityData,
                owmCode = owmCode,
                owmHourlyCode = owmHourlyCode,
                weatherApiCode = weatherApiCode,
                useOwmForCurrent = useOwmForCurrent,
                isLoading = false,
            )
        }
    }
}
