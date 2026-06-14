package ru.plumsoftware.weatherforecastru.messanging.local

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecastru.messanging.WeatherNotificationHelper
import ru.plumsoftware.weatherforecastru.messanging.WeatherNotificationSender

class SimpleNotificationService(private val context: Context) {

    fun showNotification() {
        CoroutineScope(Dispatchers.IO).launch {
            WeatherNotificationSender.fetchAndShow(context.applicationContext)
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = WeatherNotificationHelper.NOTIFICATION_CHANNEL_ID
    }
}
