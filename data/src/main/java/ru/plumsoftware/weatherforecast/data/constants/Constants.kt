package ru.plumsoftware.weatherforecast.data.constants

sealed class Constants {
    object SharedPreferences {
        const val SHARED_PREF_NAME = "ru.plumsoftware.main.sharedpref"
        const val SHARED_PREF_THEME = "ru.plumsoftware.main.sharedpref.theme"
        const val SHARED_PREF_CITY = "ru.plumsoftware.main.sharedpref.city"
        const val SHARED_PREF_COUNTRY = "ru.plumsoftware.main.sharedpref.country"
        const val SHARED_PREF_SHOW_TIPS = "ru.plumsoftware.main.sharedpref.show_tips"
    }
}