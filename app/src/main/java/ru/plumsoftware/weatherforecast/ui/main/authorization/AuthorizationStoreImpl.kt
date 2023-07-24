package ru.plumsoftware.weatherforecast.ui.main.authorization

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlinx.coroutines.launch

internal class AuthorizationStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthorizationStore = object : AuthorizationStore,
        Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> by storeFactory.create(
            initialState = AuthorizationStore.State(),
            bootstrapper = coroutineBootstrapper {
                launch {

                }
            },
            executorFactory = ::ExecutorImpl
        ) {}


    private inner class ExecutorImpl :
        CoroutineExecutor<AuthorizationStore.Intent, Nothing, AuthorizationStore.State, Nothing, AuthorizationStore.Label>() {
        override fun executeIntent(
            intent: AuthorizationStore.Intent,
            getState: () -> AuthorizationStore.State
        ) {
            when (intent) {
                AuthorizationStore.Intent.ContinueButtonClicked -> TODO()
            }
        }
    }
}