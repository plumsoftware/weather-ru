package ru.plumsoftware.weatherforecastru.messanging.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plumsoftware.weatherforecastru.data.storage.HttpClientStorage
import ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage

object MessagingDI : KoinComponent {
    val sharedPreferencesStorage by inject<ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage>()
    val httpClientStorage by inject<ru.plumsoftware.weatherforecastru.data.storage.HttpClientStorage> ()
}