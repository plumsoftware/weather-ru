package ru.plumsoftware.weatherforecastru.presentation.noconnection.store

import com.arkivanov.mvikotlin.core.store.Store

interface NoConnectionStore :
    Store<NoConnectionStore.Intent, NoConnectionStore.State, NoConnectionStore.Label> {

    sealed interface Intent {
        object TryInternetConnection : Intent
    }

    data class State (
        val value: Int = 0
    )

    sealed interface Label {
        object TryInternetConnection : Label
    }
}