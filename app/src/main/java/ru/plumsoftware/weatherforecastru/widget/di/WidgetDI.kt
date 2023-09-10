package ru.plumsoftware.weatherforecastru.widget.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plumsoftware.weatherforecastru.domain.storage.HttpClientStorage
import ru.plumsoftware.weatherforecastru.domain.storage.SharedPreferencesStorage

object WidgetDI : KoinComponent {
    val sharedPreferencesStorage by inject<SharedPreferencesStorage>()
    val httpClientStorage by inject<HttpClientStorage> ()
}