package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

fun weatherIconForCode(iconId: Int, isNight: Boolean): Pair<ImageVector, Color> = when (iconId) {
    in 200..232 -> if (isNight) weatherIcons[13] else weatherIcons[14]
    in 300..321 -> weatherIcons[16]
    in 500..531 -> if (isNight) weatherIcons[18] else weatherIcons[17]
    in 600..621 -> if (isNight) weatherIcons[20] else weatherIcons[19]
    in 700..721 -> if (isNight) weatherIcons[12] else weatherIcons[11]
    731 -> weatherIcons[8]
    in 741..751 -> if (isNight) weatherIcons[10] else weatherIcons[9]
    in 761..771 -> weatherIcons[8]
    781 -> weatherIcons[13]
    800 -> if (isNight) weatherIcons[1] else weatherIcons[0]
    801 -> if (isNight) weatherIcons[3] else weatherIcons[2]
    802 -> if (isNight) weatherIcons[5] else weatherIcons[4]
    in 803..804 -> if (isNight) weatherIcons[7] else weatherIcons[6]
    else -> weatherIcons[0]
}

private val sunColor = Color(0xFFF5A623)
private val moonColor = Color(0xFFFFD54F)
private val cloudDay = Color(0xFFB8C4D8)
private val cloudNight = Color(0xFF8A9BBD)
private val rainColor = Color(0xFF5BA3E8)
private val snowColor = Color(0xFFB8D4F0)
private val thunderColor = Color(0xFFFFD54F)
private val dustColor = Color(0xFFE8913A)

private val weatherIcons = listOf(
    Pair(PlumsoftwareIconPack.Weather.Sunny, sunColor),
    Pair(PlumsoftwareIconPack.Weather.Moon, moonColor),
    Pair(PlumsoftwareIconPack.Weather.Cloud, cloudDay),
    Pair(PlumsoftwareIconPack.Weather.Cloud, cloudNight),
    Pair(PlumsoftwareIconPack.Weather.Cloud, cloudDay),
    Pair(PlumsoftwareIconPack.Weather.Cloud, cloudNight),
    Pair(PlumsoftwareIconPack.Weather.Doublecloud, cloudDay),
    Pair(PlumsoftwareIconPack.Weather.Doublecloud, cloudNight),
    Pair(PlumsoftwareIconPack.Weather.Dust, dustColor),
    Pair(PlumsoftwareIconPack.Weather.Hazzy, cloudDay),
    Pair(PlumsoftwareIconPack.Weather.Hazzy, cloudNight),
    Pair(PlumsoftwareIconPack.Weather.Hazzy, cloudDay),
    Pair(PlumsoftwareIconPack.Weather.Hazzy, cloudNight),
    Pair(PlumsoftwareIconPack.Weather.Tornado, cloudDay),
    Pair(PlumsoftwareIconPack.Weather.CloudThunder, thunderColor),
    Pair(PlumsoftwareIconPack.Weather.CloudThunder, thunderColor),
    Pair(PlumsoftwareIconPack.Weather.RainyDay, rainColor),
    Pair(PlumsoftwareIconPack.Weather.RainyDay, rainColor),
    Pair(PlumsoftwareIconPack.Weather.RainyNight, rainColor),
    Pair(PlumsoftwareIconPack.Weather.CloudSnowDay, snowColor),
    Pair(PlumsoftwareIconPack.Weather.CloudSnowNight, snowColor),
)
