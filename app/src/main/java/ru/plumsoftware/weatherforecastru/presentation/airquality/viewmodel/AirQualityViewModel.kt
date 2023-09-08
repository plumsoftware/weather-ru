package ru.plumsoftware.weatherforecastru.presentation.airquality.viewmodel

import androidx.lifecycle.ViewModel
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.AirQuality
import ru.plumsoftware.weatherforecastru.presentation.airquality.store.AirQualityStore
import ru.plumsoftware.weatherforecastru.presentation.airquality.store.AirQualityStoreFactory

class AirQualityViewModel (
    storeFactory: StoreFactory,
    private val output: (AirQualityViewModel.Output) -> Unit,
    airQuality: AirQuality
) : ViewModel() {

    private val airQualityStore = AirQualityStoreFactory(
        storeFactory = storeFactory,
        airQuality = airQuality
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<AirQualityStore.State> = airQualityStore.stateFlow

    val label: Flow<AirQualityStore.Label> = airQualityStore.labels

    fun onEvent(event: AirQualityStore.Intent) {
        airQualityStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object OpenContentScreen : Output()
    }
}