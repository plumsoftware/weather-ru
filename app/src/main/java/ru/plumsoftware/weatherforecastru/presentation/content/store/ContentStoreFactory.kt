package ru.plumsoftware.weatherforecastru.presentation.content.store

import android.os.Build
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import com.yandex.mobile.ads.nativeads.NativeAd
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecastru.data.map.WeatherMapLayer
import ru.plumsoftware.weatherforecastru.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecastru.data.models.settings.UserSettings
import ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import ru.plumsoftware.weatherforecastru.data.remote.dto.forecast_owm.MainWeatherResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Astro
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.hasCurrentWeather
import ru.plumsoftware.weatherforecastru.data.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.data.models.settings.WindSpeed
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date

class ContentStoreFactory(
    private val storeFactory: StoreFactory,
    private val sharedPreferencesStorage: ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage,
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): ContentStore =
        object : ContentStore,
            Store<ContentStore.Intent, ContentStore.State, ContentStore.Label> by storeFactory.create(
                name = "Content",
                initialState = ContentStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(ContentStoreFactory.Action.InitFromStorage)
                    }
                },
                reducer = ContentStoreFactory.ReducerImpl,
                executorFactory = ::ExecutorImpl
            ) {
        }

    sealed interface Action {
        object InitFromStorage : Action
    }

    sealed interface Msg {
        data class LocationData(
            val city: String,
            val country: String
        ) : Msg

        data class DropDownMenu(val value: Boolean) : Msg

        data class CheckBoxValue(val value: Boolean) : Msg

        data class ShowTipsMsg(val value: Boolean) : Msg

        data class AdsList(val value: MutableList<NativeAd>) : Msg

        data class IsAdsLoading(val value: Boolean) : Msg

        data class ChangeHourly(val value: Int) : Msg

        data class ScrollToItem(val value: Int) : Msg

        data class NeedScroll(val value: Boolean) : Msg

        data class IsDark(val value: Boolean) : Msg

        //        region::Weather
        data class OwmResponseMsg(val value: OwmResponse) : Msg
        data class OwmHourlyResponseMsg(val value: MainWeatherResponse) : Msg
        data class WeatherUnitsMsg(val value: WeatherUnits) : Msg
        data class WeatherApiResponseMsg(val value: WeatherApiResponse) : Msg
        data class WindSpeedMsg(val value: WindSpeed) : Msg
        data class OwmCode(val value: Int) : Msg
        data class OwmHourlyCode(val value: Int) : Msg
        data class WeatherApiCode(val value: Int) : Msg
        data class AirQualityDataMsg(val value: ru.plumsoftware.weatherforecastru.data.models.airquality.AirQualityData) : Msg
        data class IsWeatherLoading(val value: Boolean) : Msg
        data class UseOwmForCurrent(val value: Boolean) : Msg
        data class AstronomyAstroMsg(val value: Astro?) : Msg
        data class WeatherMapLayerMsg(val value: WeatherMapLayer) : Msg
//        endregion
    }

    private object ReducerImpl : Reducer<ContentStore.State, Msg> {

        override fun ContentStore.State.reduce(msg: Msg): ContentStore.State =
            when (msg) {
                is Msg.LocationData -> copy(
                    city = msg.city,
                    country = msg.country
                )

                is Msg.CheckBoxValue -> copy(checkBoxState = msg.value)
                is Msg.DropDownMenu -> copy(dropDownState = !msg.value)
                is Msg.OwmResponseMsg -> copy(owmResponse = msg.value)
                is Msg.OwmHourlyResponseMsg -> copy(owmHourlyResponse = msg.value)
                is Msg.WeatherUnitsMsg -> copy(weatherUnits = msg.value)
                is Msg.WeatherApiResponseMsg -> copy(weatherApiResponse = msg.value)
                is Msg.WindSpeedMsg -> copy(windSpeed = msg.value)
                is Msg.ShowTipsMsg -> copy(showTips = msg.value)
                is Msg.AdsList -> copy(adsList = msg.value)
                is Msg.IsAdsLoading -> copy(isAdsLoading = msg.value)
                is Msg.ChangeHourly -> copy(hourlyState = msg.value)
                is Msg.NeedScroll -> copy(needScroll = msg.value)
                is Msg.ScrollToItem -> copy(scrollToItem = msg.value)
                is Msg.IsDark -> copy(isDark = msg.value)
                is Msg.OwmCode -> copy(owmCode = msg.value)
                is Msg.OwmHourlyCode -> copy(owmHourlyCode = msg.value)
                is Msg.WeatherApiCode -> copy(weatherApiCode = msg.value)
                is Msg.AirQualityDataMsg -> copy(airQualityData = msg.value)
                is Msg.IsWeatherLoading -> copy(isWeatherLoading = msg.value)
                is Msg.UseOwmForCurrent -> copy(useOwmForCurrent = msg.value)
                is Msg.AstronomyAstroMsg -> copy(astronomyAstro = msg.value)
                is Msg.WeatherMapLayerMsg -> copy(weatherMapLayer = msg.value)
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<ContentStore.Intent, Action, ContentStore.State, Msg, ContentStore.Label>() {

        override fun executeIntent(
            intent: ContentStore.Intent,
            getState: () -> ContentStore.State
        ) =
            when (intent) {

                is ContentStore.Intent.CheckBoxChange -> {
                    dispatch(Msg.CheckBoxValue(value = intent.value))
                    dispatch(Msg.ShowTipsMsg(value = intent.value))
                    sharedPreferencesStorage.saveShowTips(showTips = intent.value)
                }

                is ContentStore.Intent.DropDownMenuChange -> {
                    dispatch(Msg.DropDownMenu(value = intent.value))
                }

                is ContentStore.Intent.OpenLocation -> {
                    publish(ContentStore.Label.OpenLocation)
                }

                ContentStore.Intent.OpenSettings -> {
                    publish(ContentStore.Label.OpenSettings)
                }

                is ContentStore.Intent.ChangeHourly -> {
//                    dispatch(Msg.ChangeHourly(value = intent.value))
                    dispatch(
                        Msg.ScrollToItem(
                            value = when (intent.value) {
                                0 -> 0
                                1 -> 4
                                2 -> 8
                                else -> 0
                            }
                        )
                    )
//                    dispatch(Msg.NeedScroll(value = intent.value == 0))
                }

                is ContentStore.Intent.OpenAirQuality -> {
                    publish(ContentStore.Label.OpenAirQuality)
                }

                is ContentStore.Intent.ApplyWeather -> applyWeather(intent)

                is ContentStore.Intent.ApplyWeatherLoading -> {
                    dispatch(Msg.IsWeatherLoading(value = intent.isLoading))
                }

                is ContentStore.Intent.ApplyAds -> {
                    dispatch(Msg.AdsList(value = intent.adsList))
                    dispatch(Msg.IsAdsLoading(value = intent.isAdsLoading))
                }

                is ContentStore.Intent.ApplyTheme -> {
                    dispatch(Msg.IsDark(value = intent.isDark))
                }

                is ContentStore.Intent.ChangeWeatherMapLayer -> {
                    dispatch(Msg.WeatherMapLayerMsg(value = intent.layer))
                }
            }

        override fun executeAction(action: Action, getState: () -> ContentStore.State) =
            when (action) {
                Action.InitFromStorage -> initFromStorage()
            }

        private fun applyWeather(intent: ContentStore.Intent.ApplyWeather) {
            scope.launch {
                with(sharedPreferencesStorage.get()) {
                    dispatch(
                        Msg.LocationData(
                            city = city.orEmpty(),
                            country = country.orEmpty(),
                        )
                    )
                    dispatch(Msg.WeatherUnitsMsg(value = weatherUnits))
                    dispatch(Msg.WindSpeedMsg(value = windSpeed))
                    dispatch(Msg.OwmCode(value = intent.owmCode))
                    if (intent.owmCode !in 300..599) {
                        dispatch(Msg.OwmResponseMsg(value = intent.owmResponse))
                    }
                    dispatch(Msg.OwmHourlyCode(value = intent.owmHourlyCode))
                    if (intent.owmHourlyCode !in 300..599 ||
                        intent.owmHourlyResponse.weatherList.isNotEmpty()
                    ) {
                        dispatch(Msg.OwmHourlyResponseMsg(value = intent.owmHourlyResponse))
                    }
                    dispatch(Msg.WeatherApiCode(value = intent.weatherApiCode))
                    if (intent.weatherApiCode !in 300..599 ||
                        intent.weatherApiResponse.forecast?.forecastday.orEmpty().isNotEmpty() ||
                        intent.weatherApiResponse.hasCurrentWeather()
                    ) {
                        dispatch(Msg.WeatherApiResponseMsg(value = intent.weatherApiResponse))
                    }
                    dispatch(Msg.UseOwmForCurrent(value = intent.useOwmForCurrent))
                    dispatch(Msg.AstronomyAstroMsg(value = intent.astronomyAstro))
                    dispatch(Msg.AirQualityDataMsg(value = intent.airQualityData))
                    dispatch(Msg.IsWeatherLoading(value = false))
                }
            }
        }

        private fun initFromStorage() {
            scope.launch {
                with(sharedPreferencesStorage.get()) {
                    dispatch(
                        Msg.LocationData(
                            city = city.orEmpty(),
                            country = country.orEmpty(),
                        )
                    )
                    dispatch(Msg.CheckBoxValue(value = showTips))
                    dispatch(Msg.ShowTipsMsg(value = showTips))
                    dispatch(Msg.WeatherUnitsMsg(value = weatherUnits))
                    dispatch(Msg.WindSpeedMsg(value = windSpeed))
                }
            }
        }
    }
}