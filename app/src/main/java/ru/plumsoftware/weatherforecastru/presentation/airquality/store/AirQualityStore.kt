package ru.plumsoftware.weatherforecastru.presentation.airquality.store

import com.arkivanov.mvikotlin.core.store.Store
import ru.plumsoftware.weatherforecastru.data.models.airquality.AirQualityData

interface AirQualityStore :
    Store<AirQualityStore.Intent, AirQualityStore.State, AirQualityStore.Label> {

    sealed interface Intent {
        object BackButtonClicked : Intent
    }

    data class State(
        val isLoading: Boolean = false,
        val airQualityData: AirQualityData = AirQualityData(),
        val error: String? = null,
    )

    sealed interface Label {
        object BackButtonClicked : Label
    }
}
