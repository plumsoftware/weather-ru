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
import ru.plumsoftware.weatherforecastru.data.utilities.showToast
import ru.plumsoftware.weatherforecastru.data.models.location.Location
import ru.plumsoftware.weatherforecastru.presentation.location.store.LocationStore
import ru.plumsoftware.weatherforecastru.presentation.location.store.LocationStoreFactory

class LocationViewModel(
    storeFactory: StoreFactory,
    sharedPreferencesStorage: ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage,
    private val output: (Output) -> Unit,
    private val locationItemDao: LocationItemDao
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
        with(location) {
            locationItemDao.upsert(
                locationItem = _root_ide_package_.ru.plumsoftware.weatherforecastru.data.models.location.LocationItem(
                    city = city
                )
            )
        }
    }

    suspend fun delete(location: _root_ide_package_.ru.plumsoftware.weatherforecastru.data.models.location.LocationItem) {
        locationItemDao.delete(locationItem = location)
    }

    private suspend fun checkLocation(location: Location) {
        val locationItems: List<_root_ide_package_.ru.plumsoftware.weatherforecastru.data.models.location.LocationItem> = locationItemDao.getAll()
        with(location) {
            locationItems.forEachIndexed { index, locationItem ->
                if (locationItem.city == city) {
                    showToast(context = ru.plumsoftware.weatherforecastru.application.App.INSTANCE.applicationContext, message = "Такой город уже добавлен") // TODO()
                } else {

                }
            }

            locationItemDao.upsert(
                locationItem = _root_ide_package_.ru.plumsoftware.weatherforecastru.data.models.location.LocationItem(
                    city = city
                )
            )
        }
    }

    sealed class Output {
        data class OpenContentScreen(val location: Location) : Output()
        object BackStackClicked : Output()
    }

}