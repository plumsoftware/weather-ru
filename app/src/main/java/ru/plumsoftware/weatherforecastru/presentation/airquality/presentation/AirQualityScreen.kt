package ru.plumsoftware.weatherforecastru.presentation.airquality.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.plumsoftware.weatherforecastru.data.models.airquality.AqiForecastDay
import ru.plumsoftware.weatherforecastru.data.models.airquality.Pollutant
import ru.plumsoftware.weatherforecastru.presentation.airquality.store.AirQualityStore
import ru.plumsoftware.weatherforecastru.presentation.airquality.viewmodel.AirQualityViewModel
import ru.plumsoftware.weatherforecastru.presentation.ui.Dimens
import ru.plumsoftware.weatherforecastru.presentation.ui.NavigationBarSpacer
import ru.plumsoftware.weatherforecastru.presentation.ui.components.AqiHeroCard
import ru.plumsoftware.weatherforecastru.presentation.ui.components.BackTopBar
import ru.plumsoftware.weatherforecastru.presentation.ui.components.WeatherCard
import ru.plumsoftware.weatherforecastru.presentation.ui.components.aqiColor
import ru.plumsoftware.weatherforecastru.presentation.ui.components.pollutantBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirQualityScreen(airQualityViewModel: AirQualityViewModel) {
    val state by airQualityViewModel.state.collectAsState()

    LaunchedEffect(airQualityViewModel) {
        airQualityViewModel.label.collect { label ->
            when (label) {
                AirQualityStore.Label.BackButtonClicked -> {
                    airQualityViewModel.onOutput(AirQualityViewModel.Output.OpenContentScreen)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            BackTopBar(
                title = "Состав воздуха",
                onBack = { airQualityViewModel.onEvent(AirQualityStore.Intent.BackButtonClicked) },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = androidx.compose.foundation.layout.WindowInsets(0, 0, 0, 0),
    ) { padding ->
        if (state.isLoading) {
            Box(Modifier.fillMaxWidth().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            return@Scaffold
        }

        val data = state.airQualityData
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(padding),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                start = Dimens.screenPaddingH,
                end = Dimens.screenPaddingH,
                top = Dimens.screenPaddingV,
                bottom = Dimens.screenPaddingV,
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.sectionGap),
        ) {
            item {
                AqiHeroCard(
                    aqi = data.aqi,
                    label = data.aqiLabel,
                    description = data.aqiDescription,
                )
            }
            if (data.forecast.isNotEmpty()) {
                item { AqiForecastRow(forecast = data.forecast) }
            }
            item {
                Text(
                    "Загрязнители",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            items(data.pollutants) { pollutant ->
                PollutantCard(pollutant = pollutant)
            }
            item {
                Text(
                    "Данные: Open-Meteo Air Quality API",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
            item { NavigationBarSpacer() }
        }
    }
}

@Composable
private fun AqiForecastRow(forecast: List<AqiForecastDay>) {
    WeatherCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            forecast.forEachIndexed { index, day ->
                val color = aqiColor(day.aqi)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        day.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(day.aqiLabel, style = MaterialTheme.typography.titleSmall, color = color)
                    Text(
                        day.aqi.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = color.copy(alpha = 0.7f),
                    )
                }
                if (index < forecast.lastIndex) {
                    Divider(
                        modifier = Modifier
                            .height(40.dp)
                            .padding(horizontal = 8.dp),
                        color = MaterialTheme.colorScheme.outlineVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun PollutantCard(pollutant: Pollutant) {
    val barColor = pollutantBarColor(pollutant.ratio)
    WeatherCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    pollutant.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    pollutant.fullName,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                "${pollutant.value} ${pollutant.unit}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(pollutant.ratio.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(barColor),
            )
        }
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                "0",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            )
            Text(
                "норма < ${pollutant.normalMax} ${pollutant.unit}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            )
        }
    }
}
