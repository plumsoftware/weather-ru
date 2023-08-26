package ru.plumsoftware.weatherforecast.presentation.location.viewmodel

import androidx.lifecycle.ViewModel
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.plumsoftware.weatherforecast.application.App
import ru.plumsoftware.weatherforecast.data.models.location.LocationItem
import ru.plumsoftware.weatherforecast.data.models.location.LocationItemDao
import ru.plumsoftware.weatherforecast.data.utilities.showToast
import ru.plumsoftware.weatherforecast.domain.models.location.Location
import ru.plumsoftware.weatherforecast.domain.storage.LocationStorage
import ru.plumsoftware.weatherforecast.domain.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecast.presentation.location.store.LocationStore
import ru.plumsoftware.weatherforecast.presentation.location.store.LocationStoreFactory

class LocationViewModel(
    storeFactory: StoreFactory,
    sharedPreferencesStorage: SharedPreferencesStorage,
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
                locationItem = LocationItem(
                    city = city,
                    country = country
                )
            )
        }
    }

    private suspend fun checkLocation(location: Location) {
        val locationItems: List<LocationItem> = locationItemDao.getAll()
        with(location) {
            locationItems.forEachIndexed { index, locationItem ->
                if (locationItem.city == city && locationItem.country == country) {
                    showToast(context = ru.plumsoftware.weatherforecast.application.App.INSTANCE.applicationContext, message = "Такой город уже добавлен") // TODO()
                } else {

                }
            }

            locationItemDao.upsert(
                locationItem = LocationItem(
                    city = city,
                    country = country
                )
            )
        }
    }

    sealed class Output {
        data class OpenContentScreen(val location: Location) : Output()
        object BackStackClicked : Output()
    }

}