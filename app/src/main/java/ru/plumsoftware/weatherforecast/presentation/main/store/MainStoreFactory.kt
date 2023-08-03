package ru.plumsoftware.weatherforecast.presentation.main.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch

class MainStoreFactory(
    private val storeFactory: StoreFactory
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
                reducer = MainStoreFactory.ReducerImpl,
                executorFactory = ::ExecutorImpl
            ) {
        }

    sealed interface Action {
        object Init : Action
    }

    sealed interface Msg {
        data class Theme(val isDarkTheme: Boolean) : Msg
    }

    private object ReducerImpl : Reducer<MainStore.State, MainStoreFactory.Msg> {

        override fun MainStore.State.reduce(msg: MainStoreFactory.Msg): MainStore.State =
            when (msg) {
                is Msg.Theme -> copy(
                    isDarkTheme = msg.isDarkTheme
                )
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<MainStore.Intent, MainStoreFactory.Action, MainStore.State, MainStoreFactory.Msg, MainStore.Label>() {

        override fun executeIntent(
            intent: MainStore.Intent,
            getState: () -> MainStore.State
        ) =
            when (intent) {
                is MainStore.Intent.ChangeTheme -> {
                    dispatch(Msg.Theme(isDarkTheme = intent.isDarkTheme))
                    publish(MainStore.Label.ChangeTheme(isDarkTheme = intent.isDarkTheme))
                }
            }

        override fun executeAction(
            action: MainStoreFactory.Action,
            getState: () -> MainStore.State
        ) =
            when (action) {
                is Action.Init -> init()
            }

        private fun init() {
            scope.launch {
                publish(MainStore.Label.OpenAuthorization)
            }
        }
    }
}