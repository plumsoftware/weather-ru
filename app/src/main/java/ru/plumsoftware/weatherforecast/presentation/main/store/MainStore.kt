package ru.plumsoftware.weatherforecast.presentation.main.store

import com.arkivanov.mvikotlin.core.store.Store

interface MainStore :
    Store<MainStore.Intent, MainStore.State, MainStore.Label> {

    sealed interface Intent {
        object Test : Intent
    }

    data class State(
        val test: Int = 1
    )

    sealed interface Label {
        object Test : Label
    }
}