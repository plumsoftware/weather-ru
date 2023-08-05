package ru.plumsoftware.weatherforecast.presentation.location.viewmodel

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.plumsoftware.weatherforecast.application.App
import ru.plumsoftware.weatherforecast.data.location.LocationHelper
import ru.plumsoftware.weatherforecast.presentation.location.store.LocationStore
import ru.plumsoftware.weatherforecast.presentation.location.store.LocationStoreFactory

class LocationViewModel(
    private val storeFactory: StoreFactory,
    private val output: (LocationViewModel.Output) -> Unit,
) {

    private val locationHelper = LocationHelper(App.INSTANCE)

    private val locationStore = LocationStoreFactory(
        storeFactory = storeFactory,
        locationHelper = locationHelper
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<LocationStore.State> = locationStore.stateFlow

    val label: Flow<LocationStore.Label> = locationStore.labels

    fun onEvent(event: LocationStore.Intent) {
        locationStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class OpenContentScreen(val city: String) : Output()
        object OpenAuthorizationScreen : Output()
    }

}