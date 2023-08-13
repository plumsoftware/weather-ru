package ru.plumsoftware.weatherforecast.presentation.content.viewmodel

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.plumsoftware.weatherforecast.domain.models.Location
import ru.plumsoftware.weatherforecast.domain.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecast.presentation.content.store.ContentStore
import ru.plumsoftware.weatherforecast.presentation.content.store.ContentStoreFactory

class ContentViewModel(
    storeFactory: StoreFactory,
    sharedPreferencesStorage: SharedPreferencesStorage,
    private val output: (ContentViewModel.Output) -> Unit,
) {
    private val contentStore = ContentStoreFactory(
        storeFactory = storeFactory,
        sharedPreferencesStorage = sharedPreferencesStorage
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ContentStore.State> = contentStore.stateFlow

    val label: Flow<ContentStore.Label> = contentStore.labels

    fun onEvent(event: ContentStore.Intent) {
        contentStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object OpenLocationScreen : Output()
    }
}