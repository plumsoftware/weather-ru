package ru.plumsoftware.weatherforecastru.presentation.aboutapp.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch

class AboutAppStoreFactory(
    private val storeFactory: StoreFactory,
    private val version: String,
    private val appName: String
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AboutAppStore =
        object : AboutAppStore,
            Store<AboutAppStore.Intent, AboutAppStore.State, AboutAppStore.Label> by storeFactory.create(
                name = "About",
                initialState = AboutAppStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch { dispatch(Action.AboutAction(version, appName)) }
                },
                reducer = ReducerImpl,
                executorFactory = ::ExecutorImpl
            ) {
        }

    sealed interface Action {
        data class AboutAction(val version: String, val appName: String) : Action
    }

    sealed interface Msg {
        data class AboutMsg(
            val version: String,
            val appName: String
        ) : Msg
    }

    private object ReducerImpl : Reducer<AboutAppStore.State, Msg> {

        override fun AboutAppStore.State.reduce(msg: Msg): AboutAppStore.State =
            when (msg) {
                is Msg.AboutMsg -> copy(version = version, appName = appName)
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<AboutAppStore.Intent, Action, AboutAppStore.State, Msg, AboutAppStore.Label>() {

        override fun executeIntent(
            intent: AboutAppStore.Intent,
            getState: () -> AboutAppStore.State
        ) =
            when (intent) {
                AboutAppStore.Intent.BackButtonClicked -> {
                    publish(AboutAppStore.Label.BackButtonClicked)
                }
            }

        override fun executeAction(
            action: Action,
            getState: () -> AboutAppStore.State
        ) =
            when (action) {
                is Action.AboutAction -> initAirQuality(version, appName)
            }

        private fun initAirQuality(version: String, appName: String) {
            scope.launch {
                dispatch(Msg.AboutMsg(version, appName))
            }
        }
    }
}