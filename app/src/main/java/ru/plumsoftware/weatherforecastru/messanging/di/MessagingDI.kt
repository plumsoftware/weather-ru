package ru.plumsoftware.weatherforecastru.messanging.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plumsoftware.weatherforecastru.domain.storage.HttpClientStorage
import ru.plumsoftware.weatherforecastru.domain.storage.SharedPreferencesStorage

object MessagingDI : KoinComponent {
    val sharedPreferencesStorage by inject<SharedPreferencesStorage>()
    val httpClientStorage by inject<HttpClientStorage> ()
}