package ru.plumsoftware.weatherforecastru.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.plumsoftware.weatherforecastru.di.databaseModule
import ru.plumsoftware.weatherforecastru.di.domainModuleDI
import ru.plumsoftware.weatherforecastru.di.httpClientModel
import ru.plumsoftware.weatherforecastru.domain.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecastru.messanging.local.SimpleNotificationService

class App : Application(), KoinComponent {

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val sharedPreferencesStorage: SharedPreferencesStorage by inject()
            createNotificationChannel(sharedPreferencesStorage = sharedPreferencesStorage)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(sharedPreferencesStorage: SharedPreferencesStorage) {
        with(sharedPreferencesStorage.getNotificationItem()) {
            if (period > 0) {
                val channel =
                    NotificationChannel(
                        SimpleNotificationService.NOTIFICATION_CHANNEL_ID,
                        "ru.plumsoftware.weatherforecastru.simplenotification",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )

                channel.description = "Notification with current weather and icon"

                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}
