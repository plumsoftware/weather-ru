package ru.plumsoftware.weatherforecastru.presentation.authorization.store

import com.arkivanov.mvikotlin.core.store.Store

interface AuthorizationStore :
    Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> {

    sealed interface Intent {
        object ContinueButtonClicked : Intent
    }

    data class State(val unit: Unit = Unit)

    sealed interface Label {
        object AuthorizationSuccess : Label
    }
}
