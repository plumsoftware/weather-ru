package ru.plumsoftware.weatherforecastru.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.plumsoftware.weatherforecastru.di.databaseModule
import ru.plumsoftware.weatherforecastru.di.domainModuleDI
import ru.plumsoftware.weatherforecastru.di.httpClientModel
import ru.plumsoftware.weatherforecastru.messanging.local.SimpleNotificationService

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

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel (
            SimpleNotificationService.NOTIFICATION_CHANNEL_ID,
            "ru.plumsoftware.weatherforecastru.simplenotification",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Notification with current weather and icon"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}