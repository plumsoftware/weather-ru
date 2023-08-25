package ru.plumsoftware.weatherforecast.presentation.location.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plumsoftware.weatherforecast.data.utilities.logd
import ru.plumsoftware.weatherforecast.domain.models.location.Location
import ru.plumsoftware.weatherforecast.domain.models.settings.UserSettings
import ru.plumsoftware.weatherforecast.domain.storage.LocationStorage
import ru.plumsoftware.weatherforecast.domain.storage.SharedPreferencesStorage

internal class LocationStoreFactory(
    private val storeFactory: StoreFactory,
    private val sharedPreferencesStorage: SharedPreferencesStorage
) : KoinComponent {

    private val locationStorage by inject<LocationStorage>()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): LocationStore =
        object : LocationStore,
            Store<LocationStore.Intent, LocationStore.State, LocationStore.Label> by storeFactory.create(
                name = "Location",
                initialState = LocationStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch {
//                        dispatch(LocationStoreFactory.Action.InitLocations) TODO(add later)
                        dispatch(LocationStoreFactory.Action.InitLocation)
                    }
                },
                reducer = ReducerImpl,
                executorFactory = ::ExecutorImpl
            ) {
        }

    sealed interface Action {
        object InitLocations : Action
        object InitLocation : Action
    }

    sealed interface Msg {
        data class Data(
            val city: String
        ) : Msg

        data class Country(val county: String) : Msg

        data class Error(
            val value: Boolean = false
        ) : Msg

        data class CloseIcon(
            val isVisibleCloseIcon: Boolean = false
        ) : Msg
    }

    private object ReducerImpl : Reducer<LocationStore.State, Msg> {

        override fun LocationStore.State.reduce(msg: Msg): LocationStore.State =
            when (msg) {
                is Msg.Data -> copy(
                    city = msg.city
                )

                is Msg.Error -> copy(
                    isSyntaxError = msg.value
                )

                is Msg.CloseIcon -> copy(
                    isVisibleCloseIcon = msg.isVisibleCloseIcon
                )

                is Msg.Country -> copy(
                    country = msg.county
                )
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<LocationStore.Intent, Action, LocationStore.State, Msg, LocationStore.Label>() {

        override fun executeIntent(
            intent: LocationStore.Intent,
            getState: () -> LocationStore.State
        ) =
            when (intent) {
                is LocationStore.Intent.ConfirmLocation -> TODO()

                is LocationStore.Intent.SearchButtonClicked -> {
                    with(intent.city) {
                        logd("CITY: $this")
                        sharedPreferencesStorage.save(
                            userSettings = UserSettings(
                                isDarkTheme = sharedPreferencesStorage.get().isDarkTheme,
                                city = this@with,
                                country = getState().country
                            )
                        )
                        publish(
                            LocationStore.Label.ConfirmLocation(
                                city = this@with,
                                country = getState().country
                            )
                        )
                    }
                }

                is LocationStore.Intent.BackButtonClicked -> {
                    publish(LocationStore.Label.BackButtonClicked)
                }

                is LocationStore.Intent.TextChange -> {
                    dispatch(Msg.Data(city = intent.text))
                }

                is LocationStore.Intent.TextError -> {
                    dispatch(Msg.Error(value = intent.isSyntaxError))
                }

                is LocationStore.Intent.CloseIconChange -> {
                    dispatch(Msg.CloseIcon(isVisibleCloseIcon = intent.isVisibleCloseIcon))
                }
            }

        override fun executeAction(action: Action, getState: () -> LocationStore.State) =
            when (action) {
                Action.InitLocation -> initLocation()

                Action.InitLocations -> initLocations()
            }

        private fun initLocations() {
            scope.launch {

            }
        }

        private fun initLocation() {
            scope.launch {
                val currentLocation: Location = locationStorage.get()
                with(currentLocation) {
                    dispatch(LocationStoreFactory.Msg.Data(city = city))
                    dispatch(LocationStoreFactory.Msg.Country(county = country))
                }
            }
        }
    }
}