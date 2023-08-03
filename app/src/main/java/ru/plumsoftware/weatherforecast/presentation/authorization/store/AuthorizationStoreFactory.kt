package ru.plumsoftware.weatherforecast.presentation.authorization.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch

internal class AuthorizationStoreFactory(
    private val storeFactory: StoreFactory,
    private val isDarkTheme: Boolean
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthorizationStore =
        object : AuthorizationStore,
            Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> by storeFactory.create(
                name = "Authorization",
                initialState = AuthorizationStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch { dispatch(AuthorizationStoreFactory.Action.Theme(value = isDarkTheme)) }
                },
                reducer = ReducerImpl,
                executorFactory = ::ExecutorImpl
            ) {
        }

    sealed interface Action {
        data class Theme(val value: Boolean) : Action
    }

    sealed interface Msg {
        data class Data(
            val value: Boolean
        ) : Msg
    }

    private object ReducerImpl : Reducer<AuthorizationStore.State, Msg> {

        override fun AuthorizationStore.State.reduce(msg: Msg): AuthorizationStore.State =
            when (msg) {
                is Msg.Data -> copy(
                    checkBoxValue = msg.value,
                )
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<AuthorizationStore.Intent, AuthorizationStoreFactory.Action, AuthorizationStore.State, AuthorizationStoreFactory.Msg, AuthorizationStore.Label>() {

        override fun executeIntent(
            intent: AuthorizationStore.Intent,
            getState: () -> AuthorizationStore.State
        ) =
            when (intent) {
                is AuthorizationStore.Intent.CheckBoxChanged -> {
                    dispatch(
                        AuthorizationStoreFactory.Msg.Data(
                            value = intent.value
                        )
                    )
                    publish(AuthorizationStore.Label.ThemeChanged(intent.value))
                }

                AuthorizationStore.Intent.ContinueButtonClicked -> publish(AuthorizationStore.Label.AuthorizationSuccess)
            }

        override fun executeAction(action: Action, getState: () -> AuthorizationStore.State) =
            when (action) {
                is Action.Theme -> initTheme(value = action.value)
            }

        private fun initTheme(value: Boolean) {
            scope.launch {
                dispatch(AuthorizationStoreFactory.Msg.Data(value = !value))
            }
        }
    }

}