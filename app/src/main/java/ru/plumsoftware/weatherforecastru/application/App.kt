package ru.plumsoftware.weatherforecastru.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.plumsoftware.weatherforecastru.di.databaseModule
import ru.plumsoftware.weatherforecastru.di.domainModuleDI
import ru.plumsoftware.weatherforecastru.di.httpClientModel

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(listOf(domainModuleDI, databaseModule, httpClientModel))
        }
    }
}