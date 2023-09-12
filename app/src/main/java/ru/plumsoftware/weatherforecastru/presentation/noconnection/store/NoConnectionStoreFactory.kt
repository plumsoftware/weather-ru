package ru.plumsoftware.weatherforecastru.presentation.noconnection.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor

class NoConnectionStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): NoConnectionStore =
        object : NoConnectionStore,
            Store<NoConnectionStore.Intent, NoConnectionStore.State, NoConnectionStore.Label> by storeFactory.create(
                name = "Main",
                initialState = NoConnectionStore.State(),
                executorFactory = ::ExecutorImpl
            ) {
        }

    private inner class ExecutorImpl :
        CoroutineExecutor<NoConnectionStore.Intent, Unit, NoConnectionStore.State, Unit, NoConnectionStore.Label>() {

        override fun executeIntent(
            intent: NoConnectionStore.Intent,
            getState: () -> NoConnectionStore.State
        ) = when (intent) {
            NoConnectionStore.Intent.TryInternetConnection -> publish(NoConnectionStore.Label.TryInternetConnection)
        }
    }
}