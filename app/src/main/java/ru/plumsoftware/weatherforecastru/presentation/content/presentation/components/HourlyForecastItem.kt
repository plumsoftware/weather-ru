package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.plumsoftware.uicomponents.PlumsoftwareIconPack
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.Weather
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.CloudSnowDay
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.CloudSnowNight
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Doublecloud
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.RainyDay
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.RainyNight
import ru.plumsoftware.weatherapp.weatherdata.forecast_owm.WeatherItem
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.data.constants.Constants
import ru.plumsoftware.weatherforecastru.data.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionSize
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun HourlyForecastItem(
    item: WeatherItem,
    weatherUnits: WeatherUnits,
    currentColorCover: Color
) {
    with(item) {

        val units = Pair(
            weatherUnits.unitsValue,
            weatherUnits.unitsPresentation
        ) == Constants.Settings.METRIC
        val season =
            when (getCurrentMonth()) {
                in 1..2, 12 -> Seasons.WINTER
                in 3..5 -> Seasons.SPRING
                in 6..8 -> Seasons.SUMMER
                in 9..11 -> Seasons.AUTUMN
                else -> Seasons.UNSPECIFIED
            }
        val time_ = if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalTime.now().hour >= 18 || LocalTime.now().hour <= 6
            } else {
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 18 || Calendar.getInstance()
                    .get(Calendar.HOUR_OF_DAY) <= 6
            }
        ) {
            Time.EVENING
        } else {
            Time.DAY
        }

        val icon = if (season == Seasons.WINTER) {
            if (time_ == Time.EVENING)
                PlumsoftwareIconPack.Weather.CloudSnowNight
            else
                PlumsoftwareIconPack.Weather.CloudSnowDay
        } else {
            if (rain != null) {
                if (rain.h3 > 0.0) {
                    if (time_ == Time.EVENING)
                        PlumsoftwareIconPack.Weather.RainyNight
                    else
                        PlumsoftwareIconPack.Weather.RainyDay
                } else {
                    PlumsoftwareIconPack.Weather.Doublecloud
                }
            } else {
                PlumsoftwareIconPack.Weather.Doublecloud
            }
        }
        val temp = if (units) (main.temp.toInt().toString()) else ((main.temp * 9 / 5) + 32).toInt()
            .toString()

        with(currentColorCover) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = ExtensionPaddingValues._10dp,
                    alignment = Alignment.Bottom
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .padding(all = ExtensionPaddingValues._4dp)
                    .width(width = ExtensionSize.HourlyForecastItem.width)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = ExtensionPaddingValues._10dp,
                        alignment = Alignment.Top
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(weight = 1.0f)
                ) {
                    Text(
                        text = SimpleDateFormat(
                            "dd MMMM \n HH:mm",
                            Locale.getDefault()
                        ).format(Date(dt * 1000L)),
                        style = MaterialTheme.typography.labelMedium.copy(color = this@with)
                    )
                    Icon(
                        imageVector = icon,
//                    tint = if (isSystemInDarkTheme()) Color(227, 227, 227) else Color(
//                        181,
//                        181,
//                        181
//                    ),
                        tint = LocalContentColor.current.also { this@with },
                        contentDescription = stringResource(id = R.string.weather_hour_logo_desc),
                        modifier = Modifier.width(width = ExtensionSize.IconSize._34dp)
                    )
                    if (rain != null)
                        Text(
                            text = if (season == Seasons.WINTER) "${rain.h3.toInt()}%" else "${
                                rain.h3.toInt()
                            }%",
                            style = MaterialTheme.typography.labelSmall.copy(color = this@with)
                        )
                    else
                        Text(
                            text = "0%",
                            style = MaterialTheme.typography.labelSmall.copy(color = this@with)
                        )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = ExtensionPaddingValues._10dp,
                        alignment = Alignment.Bottom
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .wrapContentHeight()
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(height = ((if (units) if (main.temp > 0) main.temp else main.temp * -1 else if ((main.temp * 9 / 5) + 32 > 0) (main.temp * 9 / 5) + 32 else (main.temp * 9 / 5) + 32 * -1)).toInt().dp)
                            .width(width = ExtensionSize.HourlyForecastItem.divWidth)
                            .clip(shape = MaterialTheme.shapes.extraSmall)
                            .background(
                                color = LocalContentColor.current,
                                shape = RoundedCornerShape(
                                    topStart = ExtensionSize.Corners._8dp,
                                    topEnd = ExtensionSize.Corners._8dp,
                                    bottomEnd = 0.dp,
                                    bottomStart = 0.dp
                                )
                            )
                            .background(
                                color = this@with,
                                shape = RoundedCornerShape(
                                    topStart = ExtensionSize.Corners._8dp,
                                    topEnd = ExtensionSize.Corners._8dp,
                                    bottomEnd = 0.dp,
                                    bottomStart = 0.dp
                                )
                            )
                    ) {}
                    Text(
                        text = "$temp°",
//                            "${if (units) "°C" else "°F"}",
                        style = MaterialTheme.typography.bodyLarge.copy(color = this@with),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

private fun getCurrentMonth(): Int {
    return java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1
}

enum class Seasons {
    WINTER, SPRING, SUMMER, AUTUMN, UNSPECIFIED
}

enum class Time {
    EVENING, DAY
}