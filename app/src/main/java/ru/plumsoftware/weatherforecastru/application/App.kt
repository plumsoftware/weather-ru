package ru.plumsoftware.weatherforecastru.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.di.databaseModule
import ru.plumsoftware.weatherforecastru.di.domainModuleDI
import ru.plumsoftware.weatherforecastru.di.httpClientModel
import ru.plumsoftware.weatherforecastru.domain.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecastru.messanging.local.SimpleNotificationService
import ru.plumsoftware.weatherforecastru.messanging.rustore.RuStorePushLogger
import ru.plumsoftware.weatherforecastru.messanging.rustore.wrapper.RuStorePushNotificationManagerWrapper
import ru.rustore.sdk.core.tasks.OnCompleteListener
import ru.rustore.sdk.pushclient.RuStorePushClient
import ru.rustore.sdk.pushclient.common.logger.DefaultLogger

class App : Application(), KoinComponent {

    companion object {
        lateinit var INSTANCE: App
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

//        region::Init tools
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(listOf(domainModuleDI, databaseModule, httpClientModel))
        }
        createNotificationPushChannel()
        RuStorePushClient.init(
            application = this,
            projectId = "gNIBO16gmI4bebwuxOopjR0wVxusImGQ",
            logger = RuStorePushLogger(tag = "ru.plumsoftware.weatherforecastru.rustore_pushes_log")
        )

        RuStorePushClient.getToken().addOnCompleteListener(object : OnCompleteListener<String> {
            override fun onFailure(throwable: Throwable) {
                Log.e("ru.plumsoftware.weatherforecastru.rustore_pushes_log", "getToken onFailure", throwable)
            }
            override fun onSuccess(result: String) {
                Log.d("ru.plumsoftware.weatherforecastru.rustore_pushes_log", "getToken onSuccess token = $result")
            }
        })
//        endregion

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

    private fun createNotificationPushChannel(){
        RuStorePushNotificationManagerWrapper.getInstance(this).createNotificationChannel(
            channelId = getString(R.string.rustore_push_notification_channel_id),
            channelName = getString(R.string.rustore_push_notification_channel_name)
        )
    }
}
