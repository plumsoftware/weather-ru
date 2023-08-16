package ru.plumsoftware.weatherforecast.presentation.content.store

import com.arkivanov.mvikotlin.core.store.Store


interface ContentStore :
    Store<ContentStore.Intent, ContentStore.State, ContentStore.Label> {

    sealed interface Intent {
        data class OpenDropDownMenu(val dropDownExpand: Boolean) : Intent
    }

    data class State(
        val city: String = "",
        val country: String = "",
        val dropDownExpand: Boolean = false
    )

    sealed interface Label {
        object TodoLabel : Label
    }
}