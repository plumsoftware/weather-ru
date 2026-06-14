package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.plumsoftware.weatherapp.weatherdata.forecast_owm.WeatherItem
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Astro
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.DetailComponent
import ru.plumsoftware.weatherforecastru.presentation.ui.DetailMetricIcons
import ru.plumsoftware.weatherforecastru.presentation.ui.Dimens
import ru.plumsoftware.weatherforecastru.presentation.ui.components.DetailCard
import ru.plumsoftware.weatherforecastru.presentation.ui.components.SectionHeader
import ru.plumsoftware.weatherforecastru.presentation.ui.components.WeatherCard
import ru.plumsoftware.weatherforecastru.presentation.ui.components.aqiColor
import ru.plumsoftware.weatherforecastru.data.models.airquality.aqiShortLabel
import ru.plumsoftware.weatherforecastru.data.weather.OwmIconSize
import ru.plumsoftware.weatherforecastru.data.weather.WeatherIconCodes
import ru.plumsoftware.weatherforecastru.presentation.ui.components.OwmWeatherIcon
import ru.plumsoftware.weatherforecastru.presentation.ui.bold
import ru.plumsoftware.weatherforecastru.presentation.ui.medium
import ru.plumsoftware.weatherforecastru.presentation.ui.regular
import ru.plumsoftware.weatherforecastru.presentation.ui.semiBold
import ru.plumsoftware.weatherforecastru.presentation.ui.statusBarTopPadding
import ru.plumsoftware.weatherforecastru.presentation.ui.sunnyAccentColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WeatherCollapsingTopBar(
    city: String,
    country: String,
    feelsLike: Int?,
    collapsed: Boolean,
    onMenuClick: () -> Unit,
    onOpenLocation: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenAirQuality: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val displayCity = if (country.isNotBlank()) "$city, $country" else city
    val feelsLikeLabel = stringResource(R.string.feels_like_temp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .statusBarTopPadding(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.screenPaddingH,
                    vertical = if (collapsed) 8.dp else 14.dp,
                ),
        ) {
            if (collapsed) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(horizontal = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Text(
                        text = displayCity,
                        style = MaterialTheme.typography.titleMedium.medium(),
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                    )
                    if (feelsLike != null) {
                        Text(
                            text = "$feelsLikeLabel $feelsLike°",
                            style = MaterialTheme.typography.bodySmall.medium(),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                WeatherTopBarMenu(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    onMenuClick = onMenuClick,
                    onOpenLocation = onOpenLocation,
                    onOpenSettings = onOpenSettings,
                    onOpenAirQuality = onOpenAirQuality,
                    modifier = Modifier.align(Alignment.TopEnd),
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.weight(1f),
                    ) {
                        Icon(
                            Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp),
                        )
                        Text(
                            displayCity,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                    WeatherTopBarMenu(
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        onMenuClick = onMenuClick,
                        onOpenLocation = onOpenLocation,
                        onOpenSettings = onOpenSettings,
                        onOpenAirQuality = onOpenAirQuality,
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = collapsed,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Divider(
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = Dimens.cardBorder,
            )
        }
    }
}

@Composable
private fun WeatherTopBarMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onMenuClick: () -> Unit,
    onOpenLocation: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenAirQuality: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        IconButton(onClick = { onExpandedChange(true); onMenuClick() }) {
            Icon(
                Icons.Outlined.MoreVert,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { onExpandedChange(false) }) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.location)) },
                onClick = { onExpandedChange(false); onOpenLocation() },
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.settings)) },
                onClick = { onExpandedChange(false); onOpenSettings() },
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.air_quality)) },
                onClick = { onExpandedChange(false); onOpenAirQuality() },
            )
        }
    }
}

@Composable
fun WeatherTopBar(
    city: String,
    country: String,
    onMenuClick: () -> Unit,
    onOpenLocation: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenAirQuality: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val displayCity = if (country.isNotBlank()) "$city, $country" else city

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.screenPaddingH, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f),
        ) {
            Icon(
                Icons.Outlined.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp),
            )
            Text(
                displayCity,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        WeatherTopBarMenu(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            onMenuClick = onMenuClick,
            onOpenLocation = onOpenLocation,
            onOpenSettings = onOpenSettings,
            onOpenAirQuality = onOpenAirQuality,
        )
    }
}

@Composable
fun WeatherHero(
    temperature: Int,
    description: String,
    feelsLike: Int,
    high: Int,
    low: Int,
    weatherIconCode: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.screenPaddingH)
            .padding(top = 12.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            OwmWeatherIcon(
                iconCode = weatherIconCode,
                contentDescription = description,
                modifier = Modifier.size(
                    width = Dimens.heroWeatherIconSize,
                    height = Dimens.heroWeatherIconHeight,
                ),
                size = OwmIconSize.Hero,
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = "$temperature°",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 80.sp,
                    letterSpacing = (-3).sp,
                ).bold(),
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.semiBold(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(
                R.string.weather_hero_details,
                high,
                low,
                stringResource(R.string.feels_like_temp),
                feelsLike,
            ),
            style = MaterialTheme.typography.bodySmall.medium(),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
        )
    }
}

data class HourlyUiItem(
    val time: String,
    val temp: Int,
    val precipChance: Int,
    val iconCode: String,
    val isNow: Boolean,
)

sealed interface HourlyForecastEntry {
    data class Hour(val data: HourlyUiItem) : HourlyForecastEntry
    data object DayDivider : HourlyForecastEntry
}

@Composable
fun HourlyForecastCard(entries: List<HourlyForecastEntry>) {
    WeatherCard(modifier = Modifier.padding(horizontal = Dimens.screenPaddingH)) {
        SectionHeader(
            title = stringResource(R.string.hourly_weather_forecast),
            titleFontWeight = FontWeight.Medium,
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            itemsIndexed(entries) { _, entry ->
                when (entry) {
                    is HourlyForecastEntry.Hour -> HourlyItem(hour = entry.data)
                    HourlyForecastEntry.DayDivider -> HourlyDayDivider()
                }
            }
        }
    }
}

@Composable
private fun HourlyDayDivider() {
    Box(
        modifier = Modifier
            .height(72.dp)
            .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(MaterialTheme.colorScheme.outlineVariant),
        )
    }
}

@Composable
private fun HourlyItem(hour: HourlyUiItem) {
    val timeColor = if (hour.isNow) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val tempColor = if (hour.isNow) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(52.dp)
            .height(88.dp)
            .padding(vertical = 4.dp),
    ) {
        Text(
            text = hour.time,
            style = MaterialTheme.typography.labelSmall.regular(),
            color = timeColor,
        )
        OwmWeatherIcon(
            iconCode = hour.iconCode,
            contentDescription = null,
            modifier = Modifier.size(Dimens.hourlyWeatherIconSize),
            size = OwmIconSize.Standard,
        )
        Box(
            modifier = Modifier.height(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (hour.precipChance > 0) {
                Text(
                    text = "${hour.precipChance}%",
                    style = MaterialTheme.typography.labelSmall.regular(),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "${hour.temp}°",
            style = MaterialTheme.typography.labelMedium.regular(),
            color = tempColor,
        )
    }
}

@Composable
fun WeatherDetailsGrid(
    humidity: Int,
    windSpeed: String,
    windDirection: String,
    visibilityKm: String,
    aqi: Int,
    aqiLabel: String,
    onAirQualityClick: () -> Unit,
) {
    val aqiTint = aqiColor(aqi)
    val aqiDisplay = aqiLabel.ifBlank { aqiShortLabel(aqi) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = Dimens.screenPaddingH)
            .height(220.dp),
        verticalArrangement = Arrangement.spacedBy(Dimens.itemGap),
        horizontalArrangement = Arrangement.spacedBy(Dimens.itemGap),
        userScrollEnabled = false,
        contentPadding = PaddingValues(0.dp),
    ) {
        item {
            DetailCard(
                iconRes = DetailMetricIcons.humidity,
                value = "$humidity%",
                label = stringResource(R.string.humidity),
            )
        }
        item {
            DetailCard(
                iconRes = DetailMetricIcons.wind,
                value = windSpeed,
                label = stringResource(R.string.weather_wind_detail, windDirection),
            )
        }
        item {
            DetailCard(
                iconRes = DetailMetricIcons.visibility,
                value = visibilityKm,
                label = stringResource(R.string.visibility),
            )
        }
        item {
            DetailCard(
                iconRes = DetailMetricIcons.airQuality,
                value = aqiDisplay,
                label = stringResource(R.string.weather_air_detail, aqi),
                valueColor = aqiTint,
                onClick = onAirQualityClick,
                isClickable = true,
            )
        }
    }
}

@Composable
fun MoonAstronomyCard(astro: Astro) {
    val localeTag = LocalConfiguration.current.locales[0].language
    val moonrise = parseWeatherApiSunTime(astro.moonrise)
    val moonset = parseWeatherApiSunTime(astro.moonset)
    val phaseLabel = translateMoonPhase(
        languageTag = localeTag,
        moonPhase = astro.moonPhase.orEmpty(),
    )
    val illumination = astro.moonIllumination ?: 0

    WeatherCard(modifier = Modifier.padding(horizontal = Dimens.screenPaddingH)) {
        SectionHeader(
            title = stringResource(R.string.moon),
            icon = null,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.itemGap),
        ) {
            DetailComponent(
                title = formatAstroTime(moonrise),
                description = stringResource(R.string.moonrise),
                iconRes = DetailMetricIcons.moon,
            )
            DetailComponent(
                title = formatAstroTime(moonset),
                description = stringResource(R.string.moonset),
                iconRes = DetailMetricIcons.moon,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.itemGap),
            verticalArrangement = Arrangement.spacedBy(Dimens.itemGap),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                DetailComponent(
                    title = phaseLabel,
                    description = stringResource(R.string.moon_phase),
                    iconRes = DetailMetricIcons.moon,
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                DetailComponent(
                    title = "$illumination%",
                    description = stringResource(R.string.moon_illumination),
                    iconRes = DetailMetricIcons.moon,
                )
            }
        }
    }
}

@Composable
fun SunriseSunsetCard(sunrise: Long, sunset: Long) {
    WeatherCard(modifier = Modifier.padding(horizontal = Dimens.screenPaddingH)) {
        SectionHeader(
            title = stringResource(R.string.astronomy),
            icon = null,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.itemGap),
        ) {
            val sunnyColor = sunnyAccentColor()
            DetailComponent(
                title = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(sunrise)),
                description = stringResource(R.string.sunrise),
                iconRes = DetailMetricIcons.sunrise,
                titleColor = sunnyColor,
            )
            DetailComponent(
                title = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(sunset)),
                description = stringResource(R.string.sunset),
                iconRes = DetailMetricIcons.sunset,
                titleColor = sunnyColor,
            )
        }
    }
}

fun mapHourlyItems(items: List<WeatherItem>, scrollIndex: Int): List<HourlyUiItem> {
    if (items.size <= 1) return emptyList()

    return items.take(12).mapIndexed { index, item ->
        val timePart = item.dtTxt.substringAfter(" ").take(5)
        HourlyUiItem(
            time = timePart,
            temp = item.main.temp.toInt(),
            precipChance = (item.pop * 100).toInt(),
            iconCode = WeatherIconCodes.fromOwmIcon(item.weather.firstOrNull()?.icon),
            isNow = index == scrollIndex,
        )
    }
}
