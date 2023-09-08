package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Hour
import ru.plumsoftware.weatherforecastru.domain.constants.Constants
import ru.plumsoftware.weatherforecastru.domain.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionSize
import ru.plumsoftware.weatherforecastru.presentation.ui.md_theme_text_cover

@Composable
fun HourlyWeatherForecast(
    list: List<Hour>,
    weatherUnits: WeatherUnits,
    scrollToItem: Int,
    needScroll: Boolean,
    index: Int,
    onClick: (Int) -> Unit
) {

    val units = Pair(
        weatherUnits.unitsValue,
        weatherUnits.unitsPresentation
    ) == Constants.Settings.METRIC

    val lazyListState = rememberLazyListState()


    LaunchedEffect(scrollToItem) {
        if (needScroll)
            lazyListState.animateScrollToItem(
                index = scrollToItem
            )
        else
            lazyListState.animateScrollToItem(
                index = 0
            )
    }

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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        space = ExtensionPaddingValues._10dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextButton(onClick = {
                        onClick(0)
                    }) {
                        Text(
                            text = stringResource(id = R.string.today),
                            style = MaterialTheme.typography.bodyMedium.copy(color = if (index == 0) MaterialTheme.colorScheme.onSecondaryContainer else md_theme_text_cover)
                        )
                    }
                    TextButton(onClick = {
                        onClick(1)
                    }) {
                        Text(
                            text = stringResource(id = R.string.tomorrow),
                            style = MaterialTheme.typography.bodyMedium.copy(color = if (index == 1) MaterialTheme.colorScheme.onSecondaryContainer else md_theme_text_cover)
                        )
                    }
                    TextButton(onClick = {
                        onClick(2)
                    }) {
                        Text(
                            text = stringResource(id = R.string.day_after_tomorrow),
                            style = MaterialTheme.typography.bodyMedium.copy(color = if (index == 2) MaterialTheme.colorScheme.onSecondaryContainer else md_theme_text_cover)
                        )
                    }
                }
                LazyRow(content = {
                    itemsIndexed(list) { index, item ->

                        HourlyForecastItem(
                            hour = item,
                            weatherUnits = weatherUnits,
                            currentColorCover = if (needScroll) {
                                if (index == scrollToItem) MaterialTheme.colorScheme.onSecondaryContainer else LocalContentColor.current
                            } else {
                                LocalContentColor.current
                            }
                        )
                    }
                }, state = lazyListState)
            }
        }
    }
}