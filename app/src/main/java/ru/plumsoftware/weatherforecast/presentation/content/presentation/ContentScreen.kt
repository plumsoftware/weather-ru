package ru.plumsoftware.weatherforecast.presentation.content.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.plumsoftware.weatherforecast.application.App
import ru.plumsoftware.weatherforecast.data.utilities.showToast
import ru.plumsoftware.weatherforecast.domain.models.Location
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.presentation.content.presentation.components.CityComponent
import ru.plumsoftware.weatherforecast.presentation.content.presentation.components.WeatherStatus
import ru.plumsoftware.weatherforecast.presentation.content.store.ContentStore
import ru.plumsoftware.weatherforecast.presentation.content.viewmodel.ContentViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ContentScreen(contentViewModel: ContentViewModel) {

//    region::States
    val state by contentViewModel.state.collectAsState()
//    endregion

//    region::Labels
    LaunchedEffect(contentViewModel) {
        contentViewModel.label.collect { label ->
            when (label) {
                is ContentStore.Label.OpenLocation -> {
                    contentViewModel.onOutput(ContentViewModel.Output.OpenLocationScreen)
                }
            }
        }
    }
//    endregion

    ContentScreen(
        state = state,
        contentViewModel = contentViewModel,
    )
}

@Composable
private fun ContentScreen(
    state: ContentStore.State,
    contentViewModel: ContentViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = ExtensionPaddingValues._24dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        CityComponent(
            Location(city = state.city, country = state.country),
            dropDownMenuExpanded = state.dropDownState,
            onCLickMoreVert = {
                contentViewModel.onEvent(
                    event = ContentStore.Intent.DropDownMenuChange(
                        value = state.dropDownState
                    )
                )
            },
            onCloseDropDownMenu = {
                contentViewModel.onEvent(
                    event = ContentStore.Intent.DropDownMenuChange(
                        value = state.dropDownState
                    )
                )
            },
            checkBoxValue = state.checkBoxState,
            onCheckedChange = { value ->
                contentViewModel.onEvent(
                    event = ContentStore.Intent.CheckBoxChange(
                        value = value
                    )
                )
            },
            onClickOpenLocation = {
                contentViewModel.onEvent(
                    event = ContentStore.Intent.OpenLocation
                )
            }
        )
        WeatherStatus()
    }
}