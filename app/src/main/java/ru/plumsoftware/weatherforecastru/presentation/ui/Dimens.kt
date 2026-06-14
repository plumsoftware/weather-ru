package ru.plumsoftware.weatherforecastru.presentation.ui

import androidx.compose.ui.unit.dp

object Dimens {
    val screenPaddingH = 16.dp
    val screenPaddingV = 20.dp
    val cardPadding = 14.dp
    val cardRadius = 16.dp
    val cardBorder = 0.5.dp
    val sectionGap = 12.dp
    val itemGap = 8.dp
    val iconBoxSize = 32.dp
    val iconBoxRadius = 8.dp
    val mapCollapsedHeight = 180.dp
    val dailyDayLabelWidth = 56.dp
    private const val WEATHER_ICON_SCALE = 1.3f
    val heroWeatherIconSize = 64.dp * WEATHER_ICON_SCALE * 0.8f
    val heroWeatherIconHeight = heroWeatherIconSize * 38f / 32f
    val collapsingTopBarExpandedHeight = 76.dp
    val collapsingTopBarCollapsedHeight = 72.dp
    val hourlyWeatherIconSize = 24.dp * WEATHER_ICON_SCALE
    val dailyIconSize = 24.dp * WEATHER_ICON_SCALE
    val widgetWeatherIconSize = 44.dp * WEATHER_ICON_SCALE
    val detailMetricIconSize = 18.dp * WEATHER_ICON_SCALE
    val tempRangeBarHeight = 4.dp
    val tempRangeBarWidth = 48.dp
}
