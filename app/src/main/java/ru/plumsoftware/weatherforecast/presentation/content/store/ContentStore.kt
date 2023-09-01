package ru.plumsoftware.weatherforecast.presentation.content.store

import com.arkivanov.mvikotlin.core.store.Store
import ru.plumsoftware.weatherforecast.data.remote.dto.owm.OwmResponse


interface ContentStore :
    Store<ContentStore.Intent, ContentStore.State, ContentStore.Label> {

    sealed interface Intent {
//        region::Drop down menu
        data class DropDownMenuChange(val value: Boolean) : Intent
//        endregion

//        region::Check box
        data class CheckBoxChange(val value: Boolean) : Intent
//        endregion

//        region::Navigation
        object OpenLocation : Intent
        object OpenSettings : Intent
//        endregion
    }

    data class State(
        val city: String = "",
        val country: String = "",
        val dropDownState: Boolean = false,
        val checkBoxState: Boolean = true,
        val owmResponse: OwmResponse = OwmResponse(),
        val weatherUnit: String = ""
    )

    sealed interface Label {
//        region::Navigation
        object OpenLocation : Label
        object OpenSettings : Label
//        endregion
    }
}