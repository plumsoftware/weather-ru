package ru.plumsoftware.weatherforecast.presentation.location.store

import com.arkivanov.mvikotlin.core.store.Store

interface LocationStore :
    Store<LocationStore.Intent, LocationStore.State, LocationStore.Label> {

    sealed interface Intent {
        object ContinueButtonClicked : Intent

        object BackButtonClicked : Intent

        data class ConfirmLocation(val value: String) : Intent

        data class TextChange(val text: String) : Intent

        data class TextError(val isSyntaxError: Boolean) : Intent

        data class CloseIconChange(val isVisibleCloseIcon: Boolean) : Intent
    }

    data class State(
        val city: String = "",
        val isSyntaxError: Boolean = false,
        val isVisibleCloseIcon: Boolean = false
    )

    sealed interface Label {
        object AuthorizationSuccess : Label

        object BackButtonClicked : Label

        data class ConfirmLocation(val value: String) : Label

    }
}