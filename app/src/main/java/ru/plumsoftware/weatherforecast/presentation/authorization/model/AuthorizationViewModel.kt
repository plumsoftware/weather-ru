package ru.plumsoftware.weatherforecast.presentation.authorization.model

import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.flow.Flow
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import ru.plumsoftware.weatherforecast.presentation.authorization.store.AuthorizationStore
import ru.plumsoftware.weatherforecast.presentation.authorization.store.AuthorizationStoreFactory

class AuthorizationViewModel(
    private val storeFactory: StoreFactory,
    private val output: (Output) -> Unit,
    private val theme: Boolean
) {

    private val authorizationStore = AuthorizationStoreFactory(
        storeFactory = storeFactory,
        isDarkTheme = theme
    ).create()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<AuthorizationStore.State> = authorizationStore.stateFlow

    val label: Flow<AuthorizationStore.Label> = authorizationStore.labels

    fun onEvent(event: AuthorizationStore.Intent) {
        authorizationStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object OpenLocationScreen : Output()

        data class ChangeTheme(val value: Boolean) : Output()
    }

}