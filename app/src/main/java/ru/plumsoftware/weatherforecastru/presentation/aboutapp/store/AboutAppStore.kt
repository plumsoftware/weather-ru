package ru.plumsoftware.weatherforecastru.presentation.aboutapp.store

import com.arkivanov.mvikotlin.core.store.Store

interface AboutAppStore :
    Store<AboutAppStore.Intent, AboutAppStore.State, AboutAppStore.Label> {

    sealed interface Intent {
        object BackButtonClicked : Intent
    }

    data class State(
        val version: String = "1.0",
        val appName: String = ""
    )

    sealed interface Label {
        object BackButtonClicked : Label
    }
}