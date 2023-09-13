package ru.plumsoftware.weatherforecastru.messanging.local

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class SimpleNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val service = SimpleNotificationService(context = context)
        service.showNotification()
    }
}