package ru.plumsoftware.weatherforecast.presentation.authorization.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper

internal class AuthorizationStoreFactory(
    private val storeFactory: StoreFactory
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthorizationStore =
        object : AuthorizationStore,
            Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> by storeFactory.create(
                name = "Authorization phone",
                initialState = AuthorizationStore.State(),
                bootstrapper = coroutineBootstrapper {},
                reducer = ReducerImpl,
                executorFactory = ::ExecutorImpl
            ) {
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
        CoroutineExecutor<AuthorizationStore.Intent, Nothing, AuthorizationStore.State, AuthorizationStoreFactory.Msg, AuthorizationStore.Label>() {

        override fun executeIntent(
            intent: AuthorizationStore.Intent,
            getState: () -> AuthorizationStore.State
        ) =
            when (intent) {
                is AuthorizationStore.Intent.CheckBoxChanged -> {
                    with(intent.value) {
                        publish(AuthorizationStore.Label.ThemeChanged(this@with))
                        dispatch(
                            AuthorizationStoreFactory.Msg.Data(
                                value = this@with
                            )
                        )
                    }
                }

                AuthorizationStore.Intent.ContinueButtonClicked -> publish(AuthorizationStore.Label.AuthorizationSuccess)
            }
    }

}