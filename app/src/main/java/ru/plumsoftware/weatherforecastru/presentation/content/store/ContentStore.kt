package ru.plumsoftware.weatherforecastru.presentation.content.store

import android.os.Build
import androidx.annotation.RequiresApi
import com.arkivanov.mvikotlin.core.store.Store
import com.yandex.mobile.ads.nativeads.NativeAd
import ru.plumsoftware.weatherforecastru.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.weatherforecastru.data.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.data.models.settings.WindSpeed
import ru.plumsoftware.weatherforecastru.data.remote.dto.forecast_owm.MainWeatherResponse
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


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
        object OpenAirQuality : Intent
//        endregion

        //        region::Hourly
        data class ChangeHourly(val value: Int) : Intent
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
        val weatherApiResponse: MainWeatherResponse = MainWeatherResponse(),
        val windSpeed: WindSpeed = WindSpeed(
            windValue = 0.0f,
            windPresentation = ""
        ),
        val showTips: Boolean = true,
        val adsList: MutableList<NativeAd> = mutableListOf(),
        val isAdsLoading: Boolean = true,
        val hourlyState: Int = 0,
        val scrollToItem: Int = 0,
        val needScroll: Boolean = true,
        val isDark: Boolean = false,
        val owmCode: Int = -1,
        val weatherApiCode: Int = -1
    )

    sealed interface Label {
        //        region::Navigation
        object OpenLocation : Label
        object OpenSettings : Label
        object OpenAirQuality : Label
//        endregion

        data class ChangeHourly(val value: Int) : Label
    }
}

internal fun findClosestTime(): Int {
    val times = listOf("00:00", "03:00", "06:00", "09:00", "12:00", "15:00", "18:00", "21:00")
    val currentTime: LocalTime = LocalTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    var closestTimeIndex = 0
    var minDifference = Long.MAX_VALUE

    for (i in times.indices) {
        val time = LocalTime.parse(times[i], formatter)
        val difference = kotlin.math.abs(currentTime.toSecondOfDay().toLong() - time.toSecondOfDay().toLong())

        if (difference < minDifference) {
            minDifference = difference
            closestTimeIndex = i
        }
    }

    return closestTimeIndex
}