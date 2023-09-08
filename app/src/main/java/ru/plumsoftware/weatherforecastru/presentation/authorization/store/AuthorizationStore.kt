package ru.plumsoftware.weatherforecastru.presentation.authorization.store

import com.arkivanov.mvikotlin.core.store.Store

interface AuthorizationStore :
    Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> {

    sealed interface Intent {
        object ContinueButtonClicked : Intent

        data class CheckBoxChanged(val value: Boolean) : Intent
    }

    data class State(
        val checkBoxValue: Boolean = false
    )

    sealed interface Label {
        object AuthorizationSuccess : Label

        data class ThemeChanged(val value: Boolean) : Label

    }
}