package ru.plumsoftware.weatherforecast.data.constants

sealed class Constants {
    object SharedPreferences {
        const val SHARED_PREF_NAME = "ru.plumsoftware.main.sharedpref"
        const val SHARED_PREF_THEME = "ru.plumsoftware.main.sharedpref.theme"
        const val SHARED_PREF_CITY = "ru.plumsoftware.main.sharedpref.city"
    }
}