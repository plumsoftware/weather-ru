package ru.plumsoftware.weatherforecastru.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.plumsoftware.weatherforecastru.domain.storage.HttpClientStorage
import ru.plumsoftware.weatherforecastru.presentation.main.store.MainStore
import ru.plumsoftware.weatherforecastru.presentation.main.store.MainStoreFactory

class MainViewModel(
    storeFactory: StoreFactory,
    private val output: (MainViewModel.Output) -> Unit,
    city: String,
    httpClientStorage: HttpClientStorage
) : ViewModel() {
    //    region::Variables
    private val mainStore = MainStoreFactory(
        storeFactory = storeFactory,
        city = city,
        httpClientStorage = httpClientStorage
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<MainStore.State> = mainStore.stateFlow

    val label: Flow<MainStore.Label> = mainStore.labels
//    endregion

    //    region::Functions
    fun onEvent(event: MainStore.Intent) {
        mainStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }
//    endregion

    sealed class Output {
        object OpenAuthorizationScreen : Output()

        data class DoHttpResponse(val city: String) : Output()
    }
}