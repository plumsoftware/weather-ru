//@file:Suppress("CAST_NEVER_SUCCEEDS")

package ru.plumsoftware.weatherforecast.presentation.content.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.plumsoftware.weatherforecast.domain.models.location.Location
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.presentation.content.presentation.components.CityComponent
import ru.plumsoftware.weatherforecast.presentation.content.presentation.components.HourlyWeatherForecast
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

                ContentStore.Label.OpenSettings -> {
                    contentViewModel.onOutput(ContentViewModel.Output.OpenSettingsScreen)
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
            space = ExtensionPaddingValues._10dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
            },
            onClickOpenSettings = {
                contentViewModel.onEvent(
                    event = ContentStore.Intent.OpenSettings
                )
            }
        )
        with(state.owmResponse) {
            WeatherStatus(
                description = weather[0].description!!,
                temp = main!!.temp!!.toInt().toString(),
                tempMax = main!!.tempMax!!.toInt().toString(),
                tempMin = main!!.tempMin!!.toInt().toString(),
                tempFeelsLike = main!!.feelsLike!!.toInt().toString(),
                weatherUnit = "",
                iconId = weather[0].id!!,
                base = base!!
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = ExtensionPaddingValues._10dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = ExtensionPaddingValues._24dp)
        ) {
            Spacer(modifier = Modifier.height(height = ExtensionPaddingValues._10dp))
            HourlyWeatherForecast(
                list = state.weatherApiResponse.forecast!!.forecastday[0].hour,
                weatherUnits = state.weatherUnits
            )
        }
    }
}