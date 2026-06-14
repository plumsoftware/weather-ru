package ru.plumsoftware.weatherforecastru.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import ru.plumsoftware.weatherforecastru.data.weather.LocalWeatherIcons
import ru.plumsoftware.weatherforecastru.data.weather.OwmIconSize
import ru.plumsoftware.weatherforecastru.data.weather.WeatherIconCodes

@Composable
fun OwmWeatherIcon(
    iconCode: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    @Suppress("UNUSED_PARAMETER") size: OwmIconSize = OwmIconSize.Standard,
) {
    val drawableRes = LocalWeatherIcons.drawableResForOwmCode(
        iconCode = WeatherIconCodes.fromOwmIcon(iconCode),
    )

    Image(
        painter = painterResource(id = drawableRes),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Fit,
    )
}
