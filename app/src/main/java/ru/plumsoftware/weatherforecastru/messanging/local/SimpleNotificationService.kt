package ru.plumsoftware.weatherforecastru.messanging.local

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.uicomponents.R as UI
import ru.plumsoftware.weatherforecastru.application.MainApplicationActivity
import ru.plumsoftware.weatherforecastru.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.weatherforecastru.messanging.di.MessagingDI
import ru.plumsoftware.weatherforecastru.widget.utilites.badIconToGoodIcon
import ru.plumsoftware.weatherforecastru.widget.utilites.doHttpResponse
import java.util.Locale

class SimpleNotificationService(private val context: Context) {

    fun showNotification() {
        CoroutineScope(Dispatchers.IO).launch {
            val httpResponse: Pair<Pair<HttpStatusCode, HttpStatusCode>, Pair<OwmResponse, WeatherApiResponse>> =
                doHttpResponse(httpClientStorage = MessagingDI.httpClientStorage)

            with(httpResponse.second.first) {
                val title = main!!.temp ?: 0.0001
                val contentText = weather[0].description!!

                if (title != 0.0001)
                    CoroutineScope(Dispatchers.Main).launch {
                        val notificationManager =
                            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        val activityIntent = Intent(context, MainApplicationActivity::class.java)

                        val largeIcon = getBitmapFromVectorDrawable(
                            context = context,
                            drawableId = badIconToGoodIcon(weather[0].id!!)
                        )

                        val pendingActivity = PendingIntent.getActivity(
                            context,
                            1,
                            activityIntent,
                            PendingIntent.FLAG_IMMUTABLE
                        )

                        val notification =
                            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                                .setSmallIcon(UI.drawable.logo)
                                .setContentTitle("\uD83D\uDD14 ${title.toInt()}${context.getString(R.string.degree_sign)}")
                                .setLargeIcon(largeIcon)
                                .setStyle(NotificationCompat.BigTextStyle())
                                .setContentText(
                                    "${
                                        contentText.substring(0, 1).uppercase(Locale.getDefault())
                                    }${contentText.substring(1)} ${context.getString(R.string.dot)} ${
                                        context.getString(
                                            R.string.see_more
                                        )
                                    }"
                                )
                                .setContentIntent(pendingActivity)
                                .build()

                        notificationManager.notify(1, notification)
                    }
            }
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "ru.plumsoftware.weatherforecastru.local.notification"
    }

    private fun getBitmapFromVectorDrawable(context: Context?, drawableId: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context!!, drawableId)
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}