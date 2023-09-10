package ru.plumsoftware.weatherforecastru.presentation.widgetconfig.viewmodel

import androidx.lifecycle.ViewModel
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.plumsoftware.weatherforecastru.domain.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.store.WidgetConfigStore
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.store.WidgetConfigStoreFactory

class WidgetConfigViewModel(
    storeFactory: StoreFactory,
    sharedPreferencesStorage: SharedPreferencesStorage,
    private val output: (Output) -> Unit,
) : ViewModel() {

    private val widgetConfigStore = WidgetConfigStoreFactory(
        storeFactory = storeFactory,
        sharedPreferencesStorage = sharedPreferencesStorage
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<WidgetConfigStore.State> = widgetConfigStore.stateFlow

    val label: Flow<WidgetConfigStore.Label> = widgetConfigStore.labels

    fun onEvent(event: WidgetConfigStore.Intent) {
        widgetConfigStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object BackStackClicked : Output()
    }

}