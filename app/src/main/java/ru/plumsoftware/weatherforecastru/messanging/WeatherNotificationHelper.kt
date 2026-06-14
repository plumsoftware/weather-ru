package ru.plumsoftware.weatherforecastru.messanging

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.uicomponents.R as UI
import ru.plumsoftware.weatherforecastru.application.MainApplicationActivity

object WeatherNotificationHelper {

    fun show(
        context: Context,
        title: String,
        body: String,
        notificationId: Int = DEFAULT_NOTIFICATION_ID,
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val activityIntent = Intent(context, MainApplicationActivity::class.java)
        val pendingActivity = PendingIntent.getActivity(
            context,
            notificationId,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )

        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.notification_logo)

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(UI.drawable.logo)
            .setLargeIcon(largeIcon)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setAutoCancel(true)
            .setContentIntent(pendingActivity)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    const val NOTIFICATION_CHANNEL_ID =
        "ru.plumsoftware.weatherforecastru.local.notification"
    private const val DEFAULT_NOTIFICATION_ID = 1001
}
