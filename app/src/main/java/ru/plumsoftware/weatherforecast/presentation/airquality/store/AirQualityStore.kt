package ru.plumsoftware.weatherforecast.presentation.airquality.store

import com.arkivanov.mvikotlin.core.store.Store
import ru.plumsoftware.weatherforecast.data.remote.dto.weatherapi.AirQuality

interface AirQualityStore :
    Store<AirQualityStore.Intent, AirQualityStore.State, AirQualityStore.Label> {

    sealed interface Intent {
        object BackButtonClicked : Intent
    }

    data class State(
        val airQuality: AirQuality = AirQuality()
    )

    sealed interface Label {
        object BackButtonClicked : Label
    }
}