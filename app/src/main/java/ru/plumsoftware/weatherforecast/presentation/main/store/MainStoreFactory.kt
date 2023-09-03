package ru.plumsoftware.weatherforecast.presentation.main.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecast.domain.storage.HttpClientStorage

class MainStoreFactory(
    private val storeFactory: StoreFactory,
    private val city: String?,
    private val httpClientStorage: HttpClientStorage
) {
    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MainStore =
        object : MainStore,
            Store<MainStore.Intent, MainStore.State, MainStore.Label> by storeFactory.create(
                name = "Main",
                initialState = MainStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch { dispatch(MainStoreFactory.Action.Init) }
                },
                executorFactory = ::ExecutorImpl
            ) {
        }

    sealed interface Action {
        object Init : Action
    }

    sealed interface Msg {

    }

    private inner class ExecutorImpl :
        CoroutineExecutor<MainStore.Intent, MainStoreFactory.Action, MainStore.State, MainStoreFactory.Msg, MainStore.Label>() {

        override fun executeAction(
            action: MainStoreFactory.Action,
            getState: () -> MainStore.State
        ) =
            when (action) {
                is Action.Init -> init()
            }

        private fun init() {
            if (city!!.isEmpty()) {
                publish(MainStore.Label.OpenAuthorization)
            } else {
                publish(
                    MainStore.Label.SkipAuthorization(city = city)
                )
            }
        }
    }
}