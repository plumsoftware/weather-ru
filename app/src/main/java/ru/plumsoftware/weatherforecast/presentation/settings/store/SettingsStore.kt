package ru.plumsoftware.weatherforecast.presentation.settings.store

import com.arkivanov.mvikotlin.core.store.Store
import ru.plumsoftware.weatherforecast.domain.models.weathermodels.WeatherUnits
import ru.plumsoftware.weatherforecast.domain.models.weathermodels.WindSpeed

interface SettingsStore :
    Store<SettingsStore.Intent, SettingsStore.State, SettingsStore.Label> {

    sealed interface Intent {
        object BackButtonClicked : Intent

        object ChangeWeatherUnits : Intent
        object ChangeWindUnits : Intent
        data class CheckBoxValue(val value: Boolean) : Intent
        object AboutApp : Intent
        object Share : Intent
        object LeaveFeedBack : Intent

    }

    data class State(
        val checkBoxValue: Boolean = false,
        val weatherUnit: WeatherUnits = WeatherUnits(
            unitsPresentation = "",
            unitsValue = ""
        ),
        val windSpeed: WindSpeed = WindSpeed(
            windPresentation = "",
            windValue = 1.0f
        )
    )

    sealed interface Label {
        object BackButtonClicked : Label

        data class ChangeTheme(val value: Boolean) : Label
        object AboutApp : Label
        object Share : Label
        object LeaveFeedBack : Label
    }
}