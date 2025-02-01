package ru.plumsoftware.weatherforecastru.presentation.settings.store

import com.arkivanov.mvikotlin.core.store.Store
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.data.models.settings.NotificationItem
import ru.plumsoftware.weatherforecastru.data.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.data.models.settings.WindSpeed

interface SettingsStore :
    Store<SettingsStore.Intent, SettingsStore.State, SettingsStore.Label> {

    sealed interface Intent {
        object BackButtonClicked : Intent

        object ChangeWeatherUnits : Intent
        object ChangeWindUnits : Intent
        data class CheckBoxValue(val value: Boolean) : Intent
        object AboutApp : Intent
        object WidgetConfigureSettings : Intent
        object Share : Intent
        object LeaveFeedBack : Intent
        data class ChangeDropDownExpanded(val value: Boolean) : Intent
        data class ChangeNotificationItem(val value: NotificationItem) : Intent

    }

    data class State(
        val checkBoxValue: Boolean = false,
        val weatherUnit: WeatherUnits = WeatherUnits(
            unitsPresentation = "",
            unitsValue = ""
        ),
        val windSpeed: WindSpeed = WindSpeed(
            windPresentation = "",
            windValue = 0.0f
        ),
        val notificationItem: NotificationItem = NotificationItem(
            namingResId = R.string.every_six_hours,
            period = 21600000
        ),
        val expandedDropDownMenu: Boolean = false
    )

    sealed interface Label {
        object BackButtonClicked : Label
        object SettingsChange : Label

        data class ChangeTheme(val value: Boolean) : Label
        object AboutApp : Label
        object Share : Label
        object LeaveFeedBack : Label
        object WidgetConfigureSettings : Label
    }
}