package ru.plumsoftware.weatherforecast.presentation.content.viewmodel

import androidx.lifecycle.ViewModel
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.yandex.mobile.ads.nativeads.NativeAd
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.plumsoftware.weatherforecast.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecast.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.weatherforecast.domain.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecast.presentation.content.store.ContentStore
import ru.plumsoftware.weatherforecast.presentation.content.store.ContentStoreFactory

class ContentViewModel(
    storeFactory: StoreFactory,
    sharedPreferencesStorage: SharedPreferencesStorage,
    owmResponse: OwmResponse,
    weatherApiResponse: WeatherApiResponse,
    adsList: MutableList<NativeAd>,
    isAdsLoading: Boolean,
    private val output: (Output) -> Unit,
) : ViewModel() {
    private val contentStore = ContentStoreFactory(
        storeFactory = storeFactory,
        sharedPreferencesStorage = sharedPreferencesStorage,
        owmResponse = owmResponse,
        weatherApiResponse = weatherApiResponse,
        adsList = adsList,
        isAdsLoading = isAdsLoading
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ContentStore.State> = contentStore.stateFlow

    val label: Flow<ContentStore.Label> = contentStore.labels

    fun onEvent(event: ContentStore.Intent) {
        contentStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object OpenLocationScreen : Output()
        object OpenSettingsScreen : Output()
    }
}