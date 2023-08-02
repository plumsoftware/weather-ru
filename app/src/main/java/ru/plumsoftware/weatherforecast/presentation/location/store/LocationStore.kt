package ru.plumsoftware.weatherforecast.presentation.location.store

import com.arkivanov.mvikotlin.core.store.Store

interface LocationStore :
    Store<LocationStore.Intent, LocationStore.State, LocationStore.Label> {

    sealed interface Intent {
        object ContinueButtonClicked : Intent

        data class ConfirmLocation(val value: String) : Intent
    }

    data class State(
        val city: String = ""
    )

    sealed interface Label {
        object AuthorizationSuccess : Label

        data class Location(val value: String) : Label

    }
}