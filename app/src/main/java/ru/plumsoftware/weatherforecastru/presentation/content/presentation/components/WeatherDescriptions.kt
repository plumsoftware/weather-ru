package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import androidx.annotation.StringRes
import ru.plumsoftware.weatherforecast.R

@StringRes
fun weatherDescriptionResForOwmId(weatherId: Int): Int = when (weatherId) {
    in 200..232 -> R.string.weather_thunderstorm
    in 300..321 -> R.string.weather_drizzle
    in 500..504 -> R.string.weather_rain
    511 -> R.string.weather_freezing_rain
    in 520..531 -> R.string.weather_rain_shower
    in 600..622 -> R.string.weather_snow
    in 701..721 -> R.string.weather_haze
    731 -> R.string.weather_dust
    in 741..762 -> R.string.weather_fog
    771 -> R.string.weather_haze
    781 -> R.string.weather_tornado
    800 -> R.string.weather_clear
    801 -> R.string.weather_partly_cloudy
    802 -> R.string.weather_cloudy
    in 803..804 -> R.string.weather_overcast
    else -> R.string.weather_cloudy
}

@StringRes
fun weatherDescriptionResForWeatherApiCode(code: Int): Int = when (code) {
    1000 -> R.string.weather_clear
    1003 -> R.string.weather_partly_cloudy
    1006 -> R.string.weather_cloudy
    1009 -> R.string.weather_overcast
    1030, 1135, 1147 -> R.string.weather_fog
    1063, 1150, 1153, 1180, 1183, 1186 -> R.string.weather_rain_light
    1189, 1192, 1195, 1198, 1201 -> R.string.weather_rain_heavy
    1240, 1243, 1246, 1249, 1252 -> R.string.weather_rain_shower
    1087, 1273, 1276, 1279 -> R.string.weather_thunderstorm
    1066, 1210, 1213, 1216, 1219, 1222, 1225, 1114, 1117, 1255, 1258 -> R.string.weather_snow
    1069, 1072, 1204, 1207, 1249, 1252 -> R.string.weather_sleet
    1168, 1171 -> R.string.weather_freezing_rain
    1237, 1261, 1264 -> R.string.weather_sleet
    1279, 1282 -> R.string.weather_snow
    else -> weatherDescriptionResForOwmId(weatherApiConditionToOwmIcon(code))
}
