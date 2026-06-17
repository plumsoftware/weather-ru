package ru.plumsoftware.weatherforecastru.presentation.location.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecastru.data.models.location.LocationItem
import ru.plumsoftware.weatherforecastru.data.models.location.LocationItemDao
import ru.plumsoftware.weatherforecastru.data.models.location.Location

internal class LocationStoreFactory(
    private val storeFactory: StoreFactory,
    private val sharedPreferencesStorage: ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage,
    private val locationItemDao: LocationItemDao
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): LocationStore =
        object : LocationStore,
            Store<LocationStore.Intent, LocationStore.State, LocationStore.Label> by storeFactory.create(
                name = "Location",
                initialState = LocationStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(LocationStoreFactory.Action.InitLocations)
                    }
                },
                reducer = ReducerImpl,
                executorFactory = ::ExecutorImpl
            ) {
        }

    sealed interface Action {
        object InitLocations : Action
    }

    sealed interface Msg {
        data class Data(
            val city: String
        ) : Msg

        data class Country(val country: String) : Msg

        data class Error(
            val value: Boolean = false
        ) : Msg

        data class CloseIcon(
            val isVisibleCloseIcon: Boolean = false
        ) : Msg

        data class Items(val items: List<_root_ide_package_.ru.plumsoftware.weatherforecastru.data.models.location.LocationItem>) : Msg

        data class ShowDialogMsg(val value: Boolean) : Msg

        data class SelectedLocationItemMsg(val value: _root_ide_package_.ru.plumsoftware.weatherforecastru.data.models.location.LocationItem) : Msg

        data class LocationDetectionDialogMsg(val value: LocationStore.LocationDetectionDialog?) : Msg

        data class DetectingLocationMsg(val value: Boolean) : Msg

        data class RequestAddressFieldFocusMsg(val value: Boolean) : Msg
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
                    country = msg.country
                )

                is Msg.Items -> copy(
                    items = msg.items
                )

                is Msg.ShowDialogMsg -> copy(showDialog = msg.value)

                is Msg.SelectedLocationItemMsg -> copy(selectedLocationItem = msg.value)

                is Msg.LocationDetectionDialogMsg -> copy(locationDetectionDialog = msg.value)

                is Msg.DetectingLocationMsg -> copy(isDetectingLocation = msg.value)

                is Msg.RequestAddressFieldFocusMsg -> copy(requestAddressFieldFocus = msg.value)
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<LocationStore.Intent, Action, LocationStore.State, Msg, LocationStore.Label>() {

        override fun executeIntent(
            intent: LocationStore.Intent,
            getState: () -> LocationStore.State
        ) =
            when (intent) {
                is LocationStore.Intent.SearchButtonClicked -> {
                    with(intent.city) {
                        sharedPreferencesStorage.saveLocation(
                            location = Location(city = this@with, country = getState().country)
                        )
                        publish(
                            LocationStore.Label.ConfirmLocation(
                                location = Location(
                                    city = this@with,
                                    country = getState().country
                                )
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

                is LocationStore.Intent.CountryChange -> {
                    dispatch(Msg.Country(country = intent.text))
                }

                is LocationStore.Intent.ShowDialog -> {
                    dispatch(Msg.ShowDialogMsg(value = intent.value))
                }

                is LocationStore.Intent.DeleteLocation -> {
                    publish(LocationStore.Label.DeleteLocation(locationItem = intent.locationItem))

                    initLocations()
                }

                is LocationStore.Intent.ChangeSelectedLocationItem -> {
                    dispatch(Msg.SelectedLocationItemMsg(value = intent.locationItem))
                }

                LocationStore.Intent.DismissLocationDetectionDialog -> {
                    dispatch(Msg.LocationDetectionDialogMsg(value = null))
                }

                LocationStore.Intent.ConfirmDetectedLocation -> {
                    val city = getState().city
                    dispatch(Msg.LocationDetectionDialogMsg(value = null))
                    if (city.isNotBlank()) {
                        publish(
                            LocationStore.Label.ConfirmLocation(
                                location = Location(
                                    city = city,
                                    country = getState().country,
                                ),
                            ),
                        )
                    } else {
                        Unit
                    }
                }

                LocationStore.Intent.EnterLocationManually -> {
                    dispatch(Msg.LocationDetectionDialogMsg(value = null))
                    dispatch(Msg.Data(city = ""))
                    dispatch(Msg.CloseIcon(isVisibleCloseIcon = false))
                    dispatch(Msg.RequestAddressFieldFocusMsg(value = true))
                }

                is LocationStore.Intent.SetDetectingLocation -> {
                    dispatch(Msg.DetectingLocationMsg(value = intent.value))
                }

                is LocationStore.Intent.ShowLocationDetectionDialog -> {
                    dispatch(Msg.LocationDetectionDialogMsg(value = intent.dialog))
                }

                is LocationStore.Intent.RequestAddressFieldFocus -> {
                    dispatch(Msg.RequestAddressFieldFocusMsg(value = intent.value))
                }
            }

        override fun executeAction(action: Action, getState: () -> LocationStore.State) =
            when (action) {
                Action.InitLocations -> initLocations()
            }

        private fun initLocations() {
            scope.launch {
                val locationItems = locationItemDao.getAll()
                    .distinctBy { it.city }
                dispatch(LocationStoreFactory.Msg.Items(items = locationItems))
            }
        }
    }
}