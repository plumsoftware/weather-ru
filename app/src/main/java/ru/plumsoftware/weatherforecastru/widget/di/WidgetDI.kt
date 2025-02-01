package ru.plumsoftware.weatherforecastru.widget.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plumsoftware.weatherforecastru.data.storage.HttpClientStorage
import ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage

object WidgetDI : KoinComponent {
    val sharedPreferencesStorage by inject<ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage>()
    val httpClientStorage by inject<ru.plumsoftware.weatherforecastru.data.storage.HttpClientStorage> ()
}