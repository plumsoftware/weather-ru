package ru.plumsoftware.weatherforecastru.presentation.aboutapp.viewmodel

import androidx.lifecycle.ViewModel
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.plumsoftware.weatherforecastru.presentation.aboutapp.store.AboutAppStore
import ru.plumsoftware.weatherforecastru.presentation.aboutapp.store.AboutAppStoreFactory

class AboutAppViewModel(
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit,
    appName: String
) : ViewModel() {
    private val aboutAppStoreFactory = AboutAppStoreFactory(
        storeFactory = storeFactory,
        appName = appName
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<AboutAppStore.State> = aboutAppStoreFactory.stateFlow

    val label: Flow<AboutAppStore.Label> = aboutAppStoreFactory.labels

    fun onEvent(event: AboutAppStore.Intent) {
        aboutAppStoreFactory.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object OpenSettingsScreen : Output()
    }
}