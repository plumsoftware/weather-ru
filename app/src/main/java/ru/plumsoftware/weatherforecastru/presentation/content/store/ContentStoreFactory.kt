package ru.plumsoftware.weatherforecastru.presentation.content.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import com.yandex.mobile.ads.nativeads.NativeAd
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecastru.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecastru.domain.models.settings.UserSettings
import ru.plumsoftware.weatherforecastru.domain.storage.SharedPreferencesStorage

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.weatherforecastru.domain.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.domain.models.settings.WindSpeed
import java.time.LocalDateTime

class ContentStoreFactory(
    private val storeFactory: StoreFactory,
    private val sharedPreferencesStorage: SharedPreferencesStorage,
    private val owmResponse: OwmResponse,
    private val weatherApiResponse: WeatherApiResponse,
    private val adsList: MutableList<NativeAd>,
    private val isAdsLoading: Boolean,
    private val isDark: Boolean,
    private val owmCode: Int,
    private val weatherApiCode: Int,
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): ContentStore =
        object : ContentStore,
            Store<ContentStore.Intent, ContentStore.State, ContentStore.Label> by storeFactory.create(
                name = "Content",
                initialState = ContentStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(ContentStoreFactory.Action.InitLocation)
                        dispatch(ContentStoreFactory.Action.InitTips)
                        dispatch(ContentStoreFactory.Action.InitWeather)
                        dispatch(ContentStoreFactory.Action.InitAds)
                        dispatch(ContentStoreFactory.Action.InitIsAdsLoading)
                        dispatch(ContentStoreFactory.Action.InitTheme)
                    }
                },
                reducer = ContentStoreFactory.ReducerImpl,
                executorFactory = ::ExecutorImpl
            ) {
        }

    sealed interface Action {
        object InitLocation : Action
        object InitTips : Action
        object InitWeather : Action
        object InitAds : Action
        object InitIsAdsLoading : Action
        object InitTheme : Action
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
        data class WeatherUnitsMsg(val value: WeatherUnits) : Msg
        data class WeatherApiResponseMsg(val value: WeatherApiResponse) : Msg
        data class WindSpeedMsg(val value: WindSpeed) : Msg
        data class OwmCode(val value: Int) : Msg
        data class WeatherApiCode(val value: Int) : Msg
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
                is Msg.WeatherApiCode -> copy(weatherApiCode = msg.value)
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
                    dispatch(Msg.ChangeHourly(value = intent.value))
                    dispatch(Msg.ScrollToItem(value = if (intent.value == 0) LocalDateTime.now().hour else 0))
                    dispatch(Msg.NeedScroll(value = intent.value == 0))
                }

                is ContentStore.Intent.OpenAirQuality -> {
                    publish(ContentStore.Label.OpenAirQuality)
                }
            }

        override fun executeAction(action: Action, getState: () -> ContentStore.State) =
            when (action) {
                is Action.InitLocation -> initLocation()
                is Action.InitTips -> initTips()
                Action.InitWeather -> initWeather(
                    sharedPreferencesStorage = sharedPreferencesStorage,
                    owmResponse = owmResponse,
                    weatherApiResponse = weatherApiResponse,
                    owmCode = owmCode,
                    weatherApiCode = weatherApiCode
                )

                Action.InitAds -> initAds(list = adsList)
                Action.InitIsAdsLoading -> initIsAdsLoading(isLoading = isAdsLoading)
                Action.InitTheme -> initTheme(isDark = isDark)
            }

        private fun initTheme(isDark: Boolean) {
            scope.launch {
                dispatch(Msg.IsDark(value = isDark))
            }
        }

        private fun initTips() {
            scope.launch {
                val showTips: Boolean = sharedPreferencesStorage.getShowTips()
                dispatch(
                    ContentStoreFactory.Msg.CheckBoxValue(
                        value = showTips
                    )
                )
            }
        }

        private fun initLocation() {
            scope.launch {
                val userSettings: UserSettings = sharedPreferencesStorage.get()
                with(userSettings) {
                    dispatch(
                        ContentStoreFactory.Msg.LocationData(
                            city = city!!,
                            country = country!!
                        )
                    )
                }
            }
        }

        private fun initWeather(
            sharedPreferencesStorage: SharedPreferencesStorage,
            owmResponse: OwmResponse,
            weatherApiResponse: WeatherApiResponse,
            owmCode: Int,
            weatherApiCode: Int
        ) {
//            region::Init weather
            scope.launch {
                with(sharedPreferencesStorage.get()) {
                    if (owmCode in 300..599) {
                        dispatch(ContentStoreFactory.Msg.OwmCode(value = owmCode))
                    } else {
                        dispatch(ContentStoreFactory.Msg.OwmResponseMsg(value = owmResponse))
                        dispatch(ContentStoreFactory.Msg.WeatherUnitsMsg(value = weatherUnits))
                        dispatch(ContentStoreFactory.Msg.WindSpeedMsg(value = windSpeed))
                        dispatch(ContentStoreFactory.Msg.ShowTipsMsg(value = showTips))
                    }

                    if (weatherApiCode in 300..599) {
                        dispatch(ContentStoreFactory.Msg.WeatherApiCode(value = weatherApiCode))
                    } else {
                        dispatch(ContentStoreFactory.Msg.WeatherApiResponseMsg(value = weatherApiResponse))
                    }
                }
            }
//            endregion
        }

        private fun initAds(list: MutableList<NativeAd>) {
            scope.launch {
                dispatch(ContentStoreFactory.Msg.AdsList(value = list))
            }
        }

        private fun initIsAdsLoading(isLoading: Boolean) {
            scope.launch {
                dispatch(ContentStoreFactory.Msg.IsAdsLoading(value = isLoading))
            }
        }
    }
}