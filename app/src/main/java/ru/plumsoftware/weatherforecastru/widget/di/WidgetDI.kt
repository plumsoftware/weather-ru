package ru.plumsoftware.weatherforecastru.widget.di

import android.content.Context
import ru.plumsoftware.weatherforecastru.data.storage.HttpClientStorage
import ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecastru.messanging.WeatherNotificationDependencies

object WidgetDI {
    fun sharedPreferencesStorage(context: Context): SharedPreferencesStorage =
        WeatherNotificationDependencies.sharedPreferencesStorage(context.applicationContext)

    fun httpClientStorage(context: Context): HttpClientStorage =
        WeatherNotificationDependencies.httpClientStorage(context.applicationContext)
}
