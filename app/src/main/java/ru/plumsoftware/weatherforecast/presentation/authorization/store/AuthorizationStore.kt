package ru.plumsoftware.weatherforecast.presentation.authorization.store

import com.arkivanov.mvikotlin.core.store.Store

interface AuthorizationStore :
    Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> {

    sealed interface Intent {
        object ContinueButtonClicked : Intent
    }

    class State

    sealed interface Label {
        object AuthorizationSuccess : Label

    }
}