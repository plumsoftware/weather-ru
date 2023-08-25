package ru.plumsoftware.weatherforecast.domain.constants

sealed class Constants {
    object SharedPreferences {
        const val SHARED_PREF_NAME = "ru.plumsoftware.main.sharedpref"
        const val SHARED_PREF_THEME = "ru.plumsoftware.main.sharedpref.theme"
        const val SHARED_PREF_CITY = "ru.plumsoftware.main.sharedpref.city"
        const val SHARED_PREF_COUNTRY = "ru.plumsoftware.main.sharedpref.country"
        const val SHARED_PREF_SHOW_TIPS = "ru.plumsoftware.main.sharedpref.show_tips"
        const val SHARED_PREF_WEATHER_UNITS_VALUE =
            "ru.plumsoftware.main.sharedpref.weather_units.value"
        const val SHARED_PREF_WEATHER_UNITS_PRESENTATION =
            "ru.plumsoftware.main.sharedpref.weather_units.presentation"
        const val SHARED_PREF_WIND_SPEED_VALUE = "ru.plumsoftware.main.sharedpref.wind_speed.value"
        const val SHARED_PREF_WIND_SPEED_PRESENTATION =
            "ru.plumsoftware.main.sharedpref.wind_speed.presentation"
    }

    object Settings {
        val METRIC = Pair<String, String>("metric", "c")
        val IMPERIAL = Pair<String, String>("imperial", "f")
        val DEFAULT = Pair<String, String>("", "k")
        val M_S = Pair<String, Float>("м/c", 1.0f)
        val MI_H = Pair<String, Float>("миль/час", 2.23f)
    }
}