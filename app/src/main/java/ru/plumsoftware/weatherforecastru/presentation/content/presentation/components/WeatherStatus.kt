package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.plumsoftware.uicomponents.PlumsoftwareIconPack
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.Weather
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Cloud
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.CloudSnowDay
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.CloudSnowNight
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.CloudThunder
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Doublecloud
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Dust
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Hazzy
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Moon
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.RainyDay
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.RainyNight
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Sunny
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Tornado
import ru.plumsoftware.weatherforecast.R as T
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionSize

@Composable
fun WeatherStatus(
    description: String,
    temp: String,
    tempMax: String,
    tempMin: String,
    tempFeelsLike: String,
    weatherUnit: String,
    base: String,
    iconId: Int,
    httpCode: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(fraction = 1.0f)
            .wrapContentHeight()
    ) {
        if (base == "") {
            CircularProgressIndicator()
        }
        if (httpCode in 300..599) {
            HttpErrorComponent(httpCode = httpCode)
        } else {
            with(
                badIconToGoodIcon(
                    icon = iconId,
                    isNight = isSystemInDarkTheme()
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = first,
                        contentDescription = stringResource(id = T.string.weather_status_logo),
                        tint = second,
                        modifier = Modifier.size(size = ExtensionSize.IconSize._44dp),
                    )
                    Spacer(modifier = Modifier.width(width = ExtensionPaddingValues._10dp))
                    Text(
                        text = "$temp°${weatherUnit.uppercase()}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 64.sp)
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = ExtensionPaddingValues._10dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(all = ExtensionPaddingValues._10dp)
            ) {
                Text(
                    text = "$tempMax°/$tempMin°, ${stringResource(id = T.string.feels_like_temp)} $tempFeelsLike°${weatherUnit.uppercase()}\n$description",
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private fun badIconToGoodIcon(icon: Int, isNight: Boolean): Pair<ImageVector, Color> {
    return when (icon) {
        in 200..232 -> {
            if (isNight) UI.weather_icons[13] else UI.weather_icons[14]
        }

        in 300..321 -> {
            UI.weather_icons[16]
        }

        in 500..531 -> {
            if (isNight) UI.weather_icons[18] else UI.weather_icons[17]
        }

        in 600..621 -> {
            if (isNight) UI.weather_icons[20] else UI.weather_icons[19]
        }

        in 700..721 -> {
            if (isNight) UI.weather_icons[12] else UI.weather_icons[11]
        }

        731 -> {
            UI.weather_icons[8]
        }

        in 741..751 -> {
            if (isNight) UI.weather_icons[10] else UI.weather_icons[9]
        }

        in 761..771 -> {
            UI.weather_icons[8]
        }

        781 -> {
            UI.weather_icons[13]
        }

        800 -> {
            if (isNight) UI.weather_icons[1] else UI.weather_icons[0]
        }

        801 -> {
            if (isNight) UI.weather_icons[3] else UI.weather_icons[2]
        }

        802 -> {
            if (isNight) UI.weather_icons[5] else UI.weather_icons[4]
        }

        in 803..804 -> {
            if (isNight) UI.weather_icons[7] else UI.weather_icons[6]
        }

        else -> {
            UI.weather_icons[0]
        }
    }
}

private object UI {
    val weather_icons = listOf<Pair<ImageVector, Color>>(
        Pair(PlumsoftwareIconPack.Weather.Sunny, Color(red = 250, green = 150, blue = 10)),
        Pair(PlumsoftwareIconPack.Weather.Moon, Color(227, 227, 227)),

        Pair(PlumsoftwareIconPack.Weather.Cloud, Color(227, 227, 227)),
        Pair(PlumsoftwareIconPack.Weather.Cloud, Color(181, 181, 181)),

        Pair(PlumsoftwareIconPack.Weather.Cloud, Color(227, 227, 227)),
        Pair(PlumsoftwareIconPack.Weather.Cloud, Color(181, 181, 181)),

        Pair(PlumsoftwareIconPack.Weather.Doublecloud, Color(227, 227, 227)),
        Pair(PlumsoftwareIconPack.Weather.Doublecloud, Color(181, 181, 181)),

        Pair(PlumsoftwareIconPack.Weather.Dust, Color(245, 175, 94)),

        Pair(PlumsoftwareIconPack.Weather.Hazzy, Color(227, 227, 227)),
        Pair(PlumsoftwareIconPack.Weather.Hazzy, Color(181, 181, 181)),

        Pair(PlumsoftwareIconPack.Weather.Hazzy, Color(227, 227, 227)),
        Pair(PlumsoftwareIconPack.Weather.Hazzy, Color(181, 181, 181)),

        Pair(PlumsoftwareIconPack.Weather.Tornado, Color(227, 227, 227)),

        Pair(PlumsoftwareIconPack.Weather.CloudThunder, Color(227, 227, 227)),
        Pair(PlumsoftwareIconPack.Weather.CloudThunder, Color(181, 181, 181)),

        Pair(PlumsoftwareIconPack.Weather.RainyDay, Color(227, 227, 227)),
        Pair(PlumsoftwareIconPack.Weather.RainyDay, Color(181, 181, 181)),
        Pair(PlumsoftwareIconPack.Weather.RainyNight, Color(227, 227, 227)),

        Pair(PlumsoftwareIconPack.Weather.CloudSnowDay, Color(227, 227, 227)),
        Pair(PlumsoftwareIconPack.Weather.CloudSnowNight, Color(181, 181, 181))
    )
}