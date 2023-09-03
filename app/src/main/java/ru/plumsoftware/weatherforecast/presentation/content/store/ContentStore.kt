package ru.plumsoftware.weatherforecast.presentation.content.store

import com.arkivanov.mvikotlin.core.store.Store
import ru.plumsoftware.weatherforecast.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecast.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.weatherforecast.domain.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecast.domain.models.settings.WindSpeed


interface ContentStore :
    Store<ContentStore.Intent, ContentStore.State, ContentStore.Label> {

    sealed interface Intent {
        //        region::Drop down menu
        data class DropDownMenuChange(val value: Boolean) : Intent
//        endregion

        //        region::Check box
        data class CheckBoxChange(val value: Boolean) : Intent
//        endregion

        //        region::Navigation
        object OpenLocation : Intent
        object OpenSettings : Intent
//        endregion
    }

    data class State(
        val city: String = "",
        val country: String = "",
        val dropDownState: Boolean = false,
        val checkBoxState: Boolean = true,
        val owmResponse: OwmResponse = OwmResponse(),
        val weatherUnits: WeatherUnits = WeatherUnits(
            unitsPresentation = "", unitsValue = ""
        ),
        val weatherApiResponse: WeatherApiResponse = WeatherApiResponse(),
        val windSpeed: WindSpeed = WindSpeed(
            windValue = 0.0f,
            windPresentation = ""
        ),
        val showTips: Boolean = true
    )

    sealed interface Label {
//        region::Navigation
        object OpenLocation : Label
        object OpenSettings : Label
//        endregion
    }
}