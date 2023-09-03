package ru.plumsoftware.weatherforecast.presentation.content.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecast.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecast.domain.models.settings.UserSettings
import ru.plumsoftware.weatherforecast.domain.storage.SharedPreferencesStorage

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import ru.plumsoftware.weatherforecast.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.weatherforecast.domain.models.settings.WeatherUnits

class ContentStoreFactory(
    private val storeFactory: StoreFactory,
    private val sharedPreferencesStorage: SharedPreferencesStorage,
    private val owmResponse: OwmResponse,
    private val weatherApiResponse: WeatherApiResponse
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
    }

    sealed interface Msg {
        data class LocationData(
            val city: String,
            val country: String
        ) : Msg

        data class DropDownMenu(val value: Boolean) : Msg

        data class CheckBoxValue(val value: Boolean) : Msg

        //        region::Weather
        data class OwmResponseMsg(val value: OwmResponse) : Msg
        data class WeatherUnitsMsg(val value: WeatherUnits) : Msg
        data class WeatherApiResponseMsg(val value: WeatherApiResponse) : Msg
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
            }

        override fun executeAction(action: Action, getState: () -> ContentStore.State) =
            when (action) {
                is Action.InitLocation -> initLocation()
                is Action.InitTips -> initTips()
                Action.InitWeather -> initWeather(
                    sharedPreferencesStorage = sharedPreferencesStorage,
                    owmResponse = owmResponse,
                    weatherApiResponse = weatherApiResponse
                )
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
            weatherApiResponse: WeatherApiResponse
        ) {
//            region::Init weather
            scope.launch {
                dispatch(ContentStoreFactory.Msg.OwmResponseMsg(value = owmResponse))
                dispatch(ContentStoreFactory.Msg.WeatherUnitsMsg(value = sharedPreferencesStorage.get().weatherUnits))
                dispatch(ContentStoreFactory.Msg.WeatherApiResponseMsg(value = weatherApiResponse))
            }
//            endregion
        }
    }
}