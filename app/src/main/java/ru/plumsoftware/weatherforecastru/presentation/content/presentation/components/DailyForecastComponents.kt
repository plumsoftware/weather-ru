package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import ru.plumsoftware.weatherforecastru.data.weather.OwmIconSize
import ru.plumsoftware.weatherforecastru.presentation.ui.components.OwmWeatherIcon
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.presentation.ui.Dimens
import ru.plumsoftware.weatherforecastru.presentation.ui.regular
import ru.plumsoftware.weatherforecastru.presentation.ui.components.SectionHeader
import ru.plumsoftware.weatherforecastru.presentation.ui.components.WeatherCard

@Composable
fun DailyForecastCard(
    items: List<DailyForecastUiItem>,
    today: LocalDate,
    modifier: Modifier = Modifier,
) {
    if (items.isEmpty()) return

    val todayLabel = stringResource(R.string.today)
    val tomorrowLabel = stringResource(R.string.tomorrow)

    val weekMin = items.minOf { it.minTemp }
    val weekMax = items.maxOf { it.maxTemp }
    val weekRange = (weekMax - weekMin).coerceAtLeast(1)

    WeatherCard(modifier = modifier.padding(horizontal = Dimens.screenPaddingH)) {
        SectionHeader(
            title = stringResource(R.string.daily_forecast_7_days),
            titleFontWeight = FontWeight.Medium,
        )
        Column {
            items.forEachIndexed { index, item ->
                DailyForecastRow(
                    item = item,
                    today = today,
                    todayLabel = todayLabel,
                    tomorrowLabel = tomorrowLabel,
                    weekMin = weekMin,
                    weekRange = weekRange,
                )
                if (index < items.lastIndex) {
                    Divider(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        thickness = Dimens.cardBorder,
                    )
                }
            }
        }
    }
}

@Composable
private fun DailyForecastRow(
    item: DailyForecastUiItem,
    today: LocalDate,
    todayLabel: String,
    tomorrowLabel: String,
    weekMin: Int,
    weekRange: Int,
) {
    val startFraction = ((item.minTemp - weekMin).toFloat() / weekRange).coerceIn(0f, 1f)
    val widthFraction = ((item.maxTemp - item.minTemp).toFloat() / weekRange).coerceIn(0.08f, 1f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.itemGap),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.itemGap),
    ) {
        Text(
            text = formatDayLabel(
                date = item.date,
                today = today,
                todayLabel = todayLabel,
                tomorrowLabel = tomorrowLabel,
            ),
            style = MaterialTheme.typography.bodyMedium.regular(),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.width(Dimens.dailyDayLabelWidth),
        )
        OwmWeatherIcon(
            iconCode = item.iconCode,
            contentDescription = null,
            modifier = Modifier.size(Dimens.dailyIconSize),
            size = OwmIconSize.Standard,
        )
        if (item.precipChance > 0) {
            Text(
                text = "${item.precipChance}%",
                style = MaterialTheme.typography.labelSmall.regular(),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.width(32.dp),
                textAlign = TextAlign.Center,
            )
        } else {
            Spacer(Modifier.width(32.dp))
        }
        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .height(Dimens.tempRangeBarHeight)
                .clip(RoundedCornerShape(Dimens.tempRangeBarHeight / 2))
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            val barWidth = (maxWidth * widthFraction).coerceAtLeast(Dimens.tempRangeBarHeight)
            val barStart = maxWidth * startFraction
            Box(
                modifier = Modifier
                    .offset(x = barStart)
                    .width(barWidth)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(Dimens.tempRangeBarHeight / 2))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.55f)),
            )
        }
        Text(
            text = "${item.minTemp}°",
            style = MaterialTheme.typography.bodyMedium.regular(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(28.dp),
            textAlign = TextAlign.End,
        )
        Text(
            text = "${item.maxTemp}°",
            style = MaterialTheme.typography.bodyMedium.regular(),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.width(28.dp),
            textAlign = TextAlign.End,
        )
    }
}
