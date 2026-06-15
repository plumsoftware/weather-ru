package ru.plumsoftware.weatherforecastru.presentation.authorization.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor

internal class AuthorizationStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): AuthorizationStore =
        object : AuthorizationStore,
            Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> by storeFactory.create(
                name = "Authorization",
                initialState = AuthorizationStore.State(),
                reducer = ReducerImpl,
                executorFactory = ::ExecutorImpl
            ) {
        }

    private object ReducerImpl : Reducer<AuthorizationStore.State, Msg> {

        override fun AuthorizationStore.State.reduce(msg: Msg): AuthorizationStore.State = this
    }

    private sealed interface Msg

    private inner class ExecutorImpl :
        CoroutineExecutor<AuthorizationStore.Intent, Nothing, AuthorizationStore.State, Msg, AuthorizationStore.Label>() {

        override fun executeIntent(
            intent: AuthorizationStore.Intent,
            getState: () -> AuthorizationStore.State
        ) {
            when (intent) {
                AuthorizationStore.Intent.ContinueButtonClicked ->
                    publish(AuthorizationStore.Label.AuthorizationSuccess)
            }
        }
    }
}
