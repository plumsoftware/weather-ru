package ru.plumsoftware.weatherforecastru.presentation.content.store

import android.os.Build
import com.arkivanov.mvikotlin.core.store.Store
import com.yandex.mobile.ads.nativeads.NativeAd
import ru.plumsoftware.weatherforecastru.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.weatherforecastru.domain.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.domain.models.settings.WindSpeed
import java.time.LocalDateTime
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
        val weatherUnits: WeatherUnits = WeatherUnits(
            unitsPresentation = "", unitsValue = ""
        ),
        val weatherApiResponse: WeatherApiResponse = WeatherApiResponse(),
        val windSpeed: WindSpeed = WindSpeed(
            windValue = 0.0f,
            windPresentation = ""
        ),
        val showTips: Boolean = true,
        val adsList: MutableList<NativeAd> = mutableListOf(),
        val isAdsLoading: Boolean = true,
        val hourlyState: Int = 0,
        val scrollToItem: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now().hour
        } else {
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        },
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