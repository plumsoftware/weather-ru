package ru.plumsoftware.weatherforecast.presentation.content.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecast.application.App
import ru.plumsoftware.weatherforecast.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecast.data.repository.OwmRepositoryImpl
import ru.plumsoftware.weatherforecast.data.utilities.showToast
import ru.plumsoftware.weatherforecast.domain.models.settings.UserSettings
import ru.plumsoftware.weatherforecast.domain.remote.dto.either.OwmEither
import ru.plumsoftware.weatherforecast.domain.storage.SharedPreferencesStorage

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.cbor.cbor
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ContentStoreFactory(
    private val storeFactory: StoreFactory,
    private val sharedPreferencesStorage: SharedPreferencesStorage,
    private val owmResponse: OwmResponse
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
        data class WeatherUnit(val value: String) : Msg
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
                is Msg.WeatherUnit -> copy(weatherUnit = "°" + msg.value.uppercase())
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
                    owmResponse = owmResponse
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
            owmResponse: OwmResponse
        ) {
//            region::Init weather
            scope.launch {
                dispatch(ContentStoreFactory.Msg.OwmResponseMsg(value = owmResponse))
                dispatch(ContentStoreFactory.Msg.WeatherUnit(value = sharedPreferencesStorage.get().weatherUnits.unitsPresentation))
            }
//            endregion
        }
    }
}