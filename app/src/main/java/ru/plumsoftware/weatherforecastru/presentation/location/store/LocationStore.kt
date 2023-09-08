package ru.plumsoftware.weatherforecastru.presentation.location.store

import androidx.compose.ui.focus.FocusRequester
import com.arkivanov.mvikotlin.core.store.Store
import ru.plumsoftware.weatherforecastru.data.models.location.LocationItem
import ru.plumsoftware.weatherforecastru.domain.models.location.Location

interface LocationStore :
    Store<LocationStore.Intent, LocationStore.State, LocationStore.Label> {

    sealed interface Intent {
        data class SearchButtonClicked(val city: String) : Intent

        object BackButtonClicked : Intent

        data class TextChange(val text: String) : Intent

        data class CountryChange(val text: String) : Intent

        data class TextError(val isSyntaxError: Boolean) : Intent

        data class CloseIconChange(val isVisibleCloseIcon: Boolean) : Intent

        data class ShowDialog(val value: Boolean) : Intent

        data class DeleteLocation(val locationItem: LocationItem) : Intent

        data class ChangeSelectedLocationItem(val locationItem: LocationItem) : Intent
    }

    data class State(
        val city: String = "",
        val country: String = "",
        val isSyntaxError: Boolean = false,
        val isVisibleCloseIcon: Boolean = false,
        val focusRequester: FocusRequester = FocusRequester(),
        val items: List<LocationItem> = emptyList(),
        val showDialog: Boolean = false,
        val selectedLocation: Location = Location(city = city, country = country),
        val selectedLocationItem: LocationItem = LocationItem(city = city)
    )

    sealed interface Label {
        object BackButtonClicked : Label

        data class ConfirmLocation(val location: Location) : Label

        data class DeleteLocation(val locationItem: LocationItem) : Label
    }
}