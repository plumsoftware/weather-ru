package ru.plumsoftware.uicomponents.plumsoftwareiconpack

import androidx.compose.ui.graphics.vector.ImageVector
import ru.plumsoftware.uicomponents.PlumsoftwareIconPack
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Cloud
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.CloudSnowDay
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.CloudSnowNight
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.CloudThunder
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Doublecloud
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Drops
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Dust
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Hazzy
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Mild
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Moon
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.PartlyCloudyDay
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.RainyDay
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.RainyNight
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.SleetyDay
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.SleetyNight
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Sunny
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Sunrise
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Sunset
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Tornado
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Windy
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.CleanNight
import kotlin.collections.List as ____KtList

public object WeatherGroup

public val PlumsoftwareIconPack.Weather: WeatherGroup
  get() = WeatherGroup

private var __AllIcons: ____KtList<ImageVector>? = null

public val WeatherGroup.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(CleanNight, Cloud, CloudSnowDay, CloudSnowNight, CloudThunder,
        Doublecloud, Drops, Dust, Hazzy, Mild, Moon, PartlyCloudyDay, RainyDay, RainyNight,
        SleetyDay, SleetyNight, Sunny, Sunrise, Sunset, Tornado, Windy)
    return __AllIcons!!
  }
