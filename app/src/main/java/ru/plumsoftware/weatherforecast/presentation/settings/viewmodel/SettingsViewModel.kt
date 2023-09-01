package ru.plumsoftware.weatherforecast.presentation.settings.viewmodel

import androidx.lifecycle.ViewModel
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.plumsoftware.weatherforecast.domain.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecast.presentation.settings.store.SettingsStore
import ru.plumsoftware.weatherforecast.presentation.settings.store.SettingsStoreFactory

class SettingsViewModel(
    storeFactory: StoreFactory,
    sharedPreferencesStorage: SharedPreferencesStorage,
    private val output: (SettingsViewModel.Output) -> Unit,
) : ViewModel() {

    private val settingsStore = SettingsStoreFactory(
        storeFactory = storeFactory,
        sharedPreferencesStorage = sharedPreferencesStorage
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<SettingsStore.State> = settingsStore.stateFlow

    val label: Flow<SettingsStore.Label> = settingsStore.labels

    fun onEvent(event: SettingsStore.Intent) {
        settingsStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object BackStackClicked : Output()
        data class ChangedTheme(val value: Boolean) : Output()
        object OnSettingsChange : Output()
    }
}