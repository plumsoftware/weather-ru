package ru.plumsoftware.weatherforecastru.data.weather

enum class OwmIconSize {
    Standard,
    Hero,
}

object WeatherIconCodes {
    const val FALLBACK = "01d"

    fun fromOwmIcon(icon: String?): String =
        icon?.trim()?.lowercase()?.takeIf { it.length >= 3 } ?: FALLBACK

    fun fromOwmWeatherId(id: Int, isDay: Boolean = true): String {
        val suffix = if (isDay) "d" else "n"
        return when (id) {
            in 200..232 -> "11$suffix"
            in 300..321 -> "09$suffix"
            in 500..504 -> "10${suffix}_l"
            511 -> "13$suffix"
            in 520..531 -> "09$suffix"
            in 600..622 -> "13$suffix"
            in 701..781 -> "50$suffix"
            800 -> if (isDay) "01d" else "01n"
            801 -> if (isDay) "02d" else "02n"
            802 -> if (isDay) "03d" else "03n"
            in 803..804 -> "04d"
            else -> if (isDay) "01d" else "01n"
        }
    }

    fun fromWeatherApiCode(code: Int, isDay: Boolean): String {
        val day = isDay
        return when (code) {
            1000 -> if (day) "01d" else "01n"
            1003 -> if (day) "02d" else "02n"
            1006 -> if (day) "03d" else "03n"
            1009 -> "04d"
            1030, 1135, 1147 -> "50d"
            1063, 1150, 1153, 1168, 1171, 1180, 1183, 1186 -> if (day) "10d_l" else "10n_l"
            1189, 1192, 1195, 1198, 1201 -> if (day) "10d_h" else "10n_h"
            1240, 1243, 1246, 1249, 1252 -> if (day) "10d_s" else "10n_s"
            1087, 1273 -> if (day) "11d_l" else "11n_l"
            1276, 1279 -> if (day) "11d_h" else "11n_h"
            1066, 1210, 1213, 1216, 1219, 1222, 1225 -> "13d"
            1114, 1117 -> "13d"
            1237, 1255, 1258, 1261, 1264 -> if (day) "c_r_s" else "10n_s"
            else -> fromOwmWeatherId(weatherApiConditionToOwmWeatherId(code), day)
        }
    }

    private fun weatherApiConditionToOwmWeatherId(code: Int): Int = when (code) {
        1000 -> 800
        1003 -> 801
        1006 -> 803
        1009 -> 804
        1030, 1135, 1147 -> 741
        1063, 1150, 1153, 1168, 1171, 1180, 1183, 1186, 1189, 1192, 1195, 1198, 1201 -> 500
        1087, 1273, 1276, 1279 -> 200
        1066, 1210, 1213, 1216, 1219, 1222, 1225 -> 600
        1114, 1117 -> 602
        1237, 1240, 1243, 1246, 1249, 1252, 1255, 1258, 1261, 1264 -> 500
        else -> 803
    }
}
