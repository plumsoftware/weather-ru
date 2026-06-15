package ru.plumsoftware.weatherforecastru.presentation.content.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.yandex.mobile.ads.nativeads.NativeAd
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecastru.data.map.MapGridWeatherRepository
import ru.plumsoftware.weatherforecastru.data.map.WeatherGridLabel
import ru.plumsoftware.weatherforecastru.data.map.WeatherMapLayer
import ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecastru.presentation.app.WeatherSession
import ru.plumsoftware.weatherforecastru.presentation.content.store.ContentStore
import ru.plumsoftware.weatherforecastru.presentation.content.store.ContentStoreFactory

class ContentViewModel(
    storeFactory: StoreFactory,
    sharedPreferencesStorage: SharedPreferencesStorage,
    private val weatherSession: WeatherSession,
    private val mapGridWeatherRepository: MapGridWeatherRepository,
    private val output: (Output) -> Unit,
) : ViewModel() {
    private val contentStore = ContentStoreFactory(
        storeFactory = storeFactory,
        sharedPreferencesStorage = sharedPreferencesStorage,
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ContentStore.State> = contentStore.stateFlow

    val label: Flow<ContentStore.Label> = contentStore.labels

    init {
        viewModelScope.launch {
            weatherSession.state.collect { session ->
                contentStore.accept(
                    ContentStore.Intent.ApplyWeatherLoading(isLoading = session.isLoading),
                )
                contentStore.accept(
                    ContentStore.Intent.ApplyWeather(
                        owmResponse = session.owmResponse,
                        owmHourlyResponse = session.owmHourlyResponse,
                        weatherApiResponse = session.weatherApiResponse,
                        astronomyAstro = session.astronomyAstro,
                        airQualityData = session.airQualityData,
                        owmCode = session.owmCode,
                        owmHourlyCode = session.owmHourlyCode,
                        weatherApiCode = session.weatherApiCode,
                        useOwmForCurrent = session.useOwmForCurrent,
                    ),
                )
            }
        }
    }

    fun updateAds(adsList: MutableList<NativeAd>, isAdsLoading: Boolean) {
        contentStore.accept(ContentStore.Intent.ApplyAds(adsList = adsList, isAdsLoading = isAdsLoading))
    }

    fun updateTheme(isDark: Boolean) {
        contentStore.accept(ContentStore.Intent.ApplyTheme(isDark = isDark))
    }

    fun onEvent(event: ContentStore.Intent) {
        contentStore.accept(event)
    }

    suspend fun loadMapGridLabels(
        latitude: Double,
        longitude: Double,
        mapLayer: WeatherMapLayer,
        unitsValue: String,
        windUnitLabel: String,
    ): List<WeatherGridLabel> {
        if (latitude == 0.0 && longitude == 0.0) return emptyList()
        return mapGridWeatherRepository.loadGridLabels(
            centerLatitude = latitude,
            centerLongitude = longitude,
            mapLayer = mapLayer,
            units = unitsValue,
            windUnitLabel = windUnitLabel,
        )
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object OpenLocationScreen : Output()
        object OpenSettingsScreen : Output()
        object OpenAirQualityScreen : Output()
    }
}
