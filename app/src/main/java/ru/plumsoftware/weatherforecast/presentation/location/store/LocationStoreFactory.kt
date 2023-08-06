package ru.plumsoftware.weatherforecast.presentation.location.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecast.application.App
import ru.plumsoftware.weatherforecast.data.location.LocationHelper
import ru.plumsoftware.weatherforecast.data.utilities.logd
import ru.plumsoftware.weatherforecast.data.utilities.showToast

internal class LocationStoreFactory(
    private val storeFactory: StoreFactory,
    private val locationHelper: LocationHelper
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): LocationStore =
        object : LocationStore,
            Store<LocationStore.Intent, LocationStore.State, LocationStore.Label> by storeFactory.create(
                name = "Location",
                initialState = LocationStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch {
//                        dispatch(LocationStoreFactory.Action.InitLocations)
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
            val value: String
        ) : Msg

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
                    city = msg.value,
                )

                is Msg.Error -> copy(
                    isSyntaxError = msg.value
                )

                is Msg.CloseIcon -> copy(
                    isVisibleCloseIcon = msg.isVisibleCloseIcon
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
                    publish(LocationStore.Label.ConfirmLocation(intent.city))
                }

                is LocationStore.Intent.BackButtonClicked -> {
                    publish(LocationStore.Label.BackButtonClicked)
                }

                is LocationStore.Intent.TextChange -> {
                    dispatch(Msg.Data(value = intent.text))
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
                if (locationHelper.isLocationEnabled()) {
                    locationHelper.getCurrentLocation { latitude, longitude, city, country ->
                        logd("LOC")
                        dispatch(LocationStoreFactory.Msg.Data(value = city!!))
                    }
                } else {
                    showToast(
                        App.INSTANCE,
                        "Определение местоположения не доступно на вашем устройстве"
                    )
                }
            }
        }
    }
}