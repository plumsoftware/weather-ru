package ru.plumsoftware.weatherforecast.presentation.main.viewmodel

import android.content.Context
import android.content.SharedPreferences
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.plumsoftware.weatherforecast.application.App
import ru.plumsoftware.weatherforecast.data.constants.Constants
import ru.plumsoftware.weatherforecast.data.utilities.logd
import ru.plumsoftware.weatherforecast.presentation.main.store.MainStore
import ru.plumsoftware.weatherforecast.presentation.main.store.MainStoreFactory

class MainViewModel(
    storeFactory: StoreFactory,
    private val output: (MainViewModel.Output) -> Unit,
    city: String
) {
    //    region::Variables
    private val mainStore = MainStoreFactory(
        storeFactory = storeFactory,
        city = city
    ).create()
    private val sharedPreferences: SharedPreferences by lazy {
        App.INSTANCE.getSharedPreferences(
            Constants.SharedPreferences.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

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
        data class ChangeTheme(val isDarkTheme: Boolean) : Output()

        object OpenAuthorizationScreen : Output()

        data class OpenContentScreen(val city: String) : Output()
    }
}