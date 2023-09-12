package ru.plumsoftware.weatherforecastru.presentation.noconnection.viewmodel

import androidx.lifecycle.ViewModel
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.plumsoftware.weatherforecastru.presentation.noconnection.store.NoConnectionStore
import ru.plumsoftware.weatherforecastru.presentation.noconnection.store.NoConnectionStoreFactory

class NoConnectionViewModel(
    storeFactory: StoreFactory,
    private val output: (NoConnectionViewModel.Output) -> Unit
) : ViewModel() {

    //    region::Variables
    private val noConnectionStore = NoConnectionStoreFactory(
        storeFactory = storeFactory
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<NoConnectionStore.State> = noConnectionStore.stateFlow

    val label: Flow<NoConnectionStore.Label> = noConnectionStore.labels
//    endregion

    //    region::Functions
    fun onEvent(event: NoConnectionStore.Intent) {
        noConnectionStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }
//    endregion

    sealed class Output {
        object TryInternetConnection : Output()
    }
}