package ru.plumsoftware.weatherforecastru.presentation.location.viewmodel

import androidx.lifecycle.ViewModel
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.plumsoftware.weatherforecastru.data.models.location.LocationItem
import ru.plumsoftware.weatherforecastru.data.models.location.LocationItemDao
import ru.plumsoftware.weatherforecastru.data.repository.LocationRepository
import ru.plumsoftware.weatherforecastru.data.models.location.Location
import ru.plumsoftware.weatherforecastru.presentation.location.store.LocationStore
import ru.plumsoftware.weatherforecastru.presentation.location.store.LocationStoreFactory

class LocationViewModel(
    storeFactory: StoreFactory,
    sharedPreferencesStorage: ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage,
    private val output: (Output) -> Unit,
    private val locationItemDao: LocationItemDao,
    private val locationRepository: LocationRepository,
) : ViewModel() {

    private val locationStore = LocationStoreFactory(
        storeFactory = storeFactory,
        sharedPreferencesStorage = sharedPreferencesStorage,
        locationItemDao = locationItemDao,
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

    suspend fun save(location: Location) {
        if (location.city.isBlank()) return
        if (locationItemDao.findByCity(location.city) != null) return
        locationItemDao.upsert(
            locationItem = LocationItem(city = location.city),
        )
    }

    suspend fun delete(location: _root_ide_package_.ru.plumsoftware.weatherforecastru.data.models.location.LocationItem) {
        locationItemDao.delete(locationItem = location)
    }

    suspend fun detectCurrentLocation() {
        val location = locationRepository.getCurrentLocation()
        if (location.city.isNotBlank()) {
            onEvent(LocationStore.Intent.CountryChange(text = ""))
            onEvent(LocationStore.Intent.TextChange(text = location.city))
            onEvent(LocationStore.Intent.SearchButtonClicked(city = location.city))
        }
    }

    sealed class Output {
        data class OpenContentScreen(val location: Location) : Output()
        object BackStackClicked : Output()
    }

}