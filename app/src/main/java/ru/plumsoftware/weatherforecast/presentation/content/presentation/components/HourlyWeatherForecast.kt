package ru.plumsoftware.weatherforecast.presentation.content.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.plumsoftware.weatherforecast.data.remote.dto.weatherapi.Hour
import ru.plumsoftware.weatherforecast.domain.constants.Constants
import ru.plumsoftware.weatherforecast.domain.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionSize

@Composable
fun HourlyWeatherForecast(list: List<Hour>, weatherUnits: WeatherUnits) {

    val units = Pair(
        weatherUnits.unitsValue,
        weatherUnits.unitsPresentation
    ) == Constants.Settings.METRIC

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
            draggedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .height(height = if (units) ExtensionSize.HourlyForecastItem.metricHeight else ExtensionSize.HourlyForecastItem.imperialHeight)
    ) {

        if (list.size == 1) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(all = ExtensionPaddingValues._14dp)
                    .fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = ExtensionPaddingValues._10dp,
                    alignment = Alignment.Bottom
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(all = ExtensionPaddingValues._14dp)
            ) {
                LazyRow(content = {
                    itemsIndexed(list) { index, item ->
                        HourlyForecastItem(
                            hour = item,
                            weatherUnits = weatherUnits
                        )
                    }
                })
            }
        }
    }
}