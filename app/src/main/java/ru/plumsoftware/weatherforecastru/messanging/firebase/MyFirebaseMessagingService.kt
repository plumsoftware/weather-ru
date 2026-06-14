package ru.plumsoftware.weatherforecastru.messanging.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.plumsoftware.weatherforecastru.messanging.WeatherNotificationHelper

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
        }

        val title = remoteMessage.notification?.title
            ?: remoteMessage.data["title"]
            ?: applicationContext.getString(
                ru.plumsoftware.weatherforecast.R.string.app_name,
            )
        val body = remoteMessage.notification?.body
            ?: remoteMessage.data["body"]
            ?: remoteMessage.data["message"]
            ?: return

        WeatherNotificationHelper.show(
            context = applicationContext,
            title = title,
            body = body,
            notificationId = remoteMessage.messageId?.hashCode() ?: DEFAULT_NOTIFICATION_ID,
        )
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    private companion object {
        const val TAG = "FirebaseMessagingService"
        const val DEFAULT_NOTIFICATION_ID = 1002
    }
}
