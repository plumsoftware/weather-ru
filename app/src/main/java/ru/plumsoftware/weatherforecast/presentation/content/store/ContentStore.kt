package ru.plumsoftware.weatherforecast.presentation.content.store

import com.arkivanov.mvikotlin.core.store.Store


interface ContentStore :
    Store<ContentStore.Intent, ContentStore.State, ContentStore.Label> {

    sealed interface Intent {
        object Todo : Intent
    }

    data class State(
        val city: String = "",
        val country: String = ""
    )

    sealed interface Label {
        object TodoLabel : Label
    }
}