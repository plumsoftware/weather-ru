package ru.plumsoftware.weatherforecastru.data.constants

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
        const val SHARED_PREF_APP_WIDGET_RADIUS =
            "ru.plumsoftware.widget.sharedpref.radius"
        const val SHARED_PREF_APP_WIDGET_COLOR_RED =
            "ru.plumsoftware.widget.sharedpref.color.red"
        const val SHARED_PREF_APP_WIDGET_COLOR_GREEN =
            "ru.plumsoftware.widget.sharedpref.color.green"
        const val SHARED_PREF_APP_WIDGET_COLOR_BLUE =
            "ru.plumsoftware.widget.sharedpref.color.blue"
        const val SHARED_PREF_FIRST =
            "ru.plumsoftware.widget.sharedpref.first"
        const val SHARED_PREF_LAUNCH_COUNT =
            "ru.plumsoftware.main.sharedpref.launch_count"
        const val SHARED_PREF_NOTIFICATION_PERIOD =
            "ru.plumsoftware.widget.sharedpref.notif_period"
        const val SHARED_PREF_NOTIFICATION_NAMING =
            "ru.plumsoftware.widget.sharedpref.notif_naming"
    }

    object Settings {
        val METRIC = Pair<String, String>("metric", "c")
        val IMPERIAL = Pair<String, String>("imperial", "f")
        val DEFAULT = Pair<String, String>("", "k")
        val M_S = Pair<String, Float>("м/с", 1.0f)
        val KM_H = Pair<String, Float>("км/ч", 3.6f)
        /** @deprecated Legacy label kept for preference migration only. */
        val MI_H = KM_H
    }

    object Database {
        const val DATABASE_NAME = "ru_plumsoftware_main_location.db"
    }

    object Links {
        const val share_link = "https://apps.rustore.ru/app/ru.plumsoftware.weatherforecastru"
        const val leaveFeedback =
            "https://apps.rustore.ru/app/ru.plumsoftware.weatherforecastru/reviews"
        const val developerEmail = "plumsoftware@yandex.ru"

        fun playStoreMarketUri(applicationId: String): String =
            "market://details?id=$applicationId"

        fun playStoreWebUri(applicationId: String): String =
            "https://play.google.com/store/apps/details?id=$applicationId"
    }
}