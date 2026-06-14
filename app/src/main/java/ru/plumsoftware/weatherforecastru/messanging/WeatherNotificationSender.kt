package ru.plumsoftware.weatherforecastru.messanging

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.gson.Gson
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.util.date.GMTDate
import ru.plumsoftware.uicomponents.R as UI
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.application.MainApplicationActivity
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.weatherforecastru.data.weather.LocalWeatherIcons
import ru.plumsoftware.weatherforecastru.data.weather.OwmIconSize
import ru.plumsoftware.weatherforecastru.data.weather.WeatherIconCodes
import ru.plumsoftware.weatherforecastru.data.weather.WeatherIconImageLoader
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.hasCurrentWeather
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.mapAlerts
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.weatherApiTemperature
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.weatherDescriptionResForWeatherApiCode

object WeatherNotificationSender {

    const val WEATHER_NOTIFICATION_ID = 1001
    const val ALERT_NOTIFICATION_ID = 1002

    suspend fun fetchAndShow(context: Context) {
        val appContext = context.applicationContext
        val sharedPreferencesStorage = WeatherNotificationDependencies.sharedPreferencesStorage(appContext)
        val httpClientStorage = WeatherNotificationDependencies.httpClientStorage(appContext)

        val forecastEither = httpClientStorage.getWeatherApiForecast<String, HttpStatusCode, GMTDate>()
        if (!forecastEither.httpStatusCode.isSuccess()) return

        val response = Gson().fromJson(forecastEither.data, WeatherApiResponse::class.java)
        if (!response.hasCurrentWeather()) return

        val settings = sharedPreferencesStorage.get()
        val current = response.current ?: return
        val conditionCode = current.condition?.code ?: return
        val isDay = current.isDay == 1
        val temperatures = weatherApiTemperature(
            current = current,
            day = response.forecast?.forecastday?.firstOrNull()?.day,
            unitsValue = settings.weatherUnits.unitsValue,
        )
        val currentTemp = temperatures.current ?: return
        val feelsLikeTemp = temperatures.feelsLike ?: currentTemp

        val iconCode = WeatherIconCodes.fromWeatherApiCode(code = conditionCode, isDay = isDay)
        val weatherIcon = WeatherIconImageLoader.loadBitmap(
            context = appContext,
            iconCode = iconCode,
            size = OwmIconSize.Standard,
        )
        val description = appContext.getString(
            weatherDescriptionResForWeatherApiCode(conditionCode),
        )
        val feelsLikeLabel = appContext.getString(R.string.feels_like_temp)
        val degreeSign = appContext.getString(R.string.degree_sign)
        val weatherTitle = "$currentTemp$degreeSign"
        val weatherBody = "$description. $feelsLikeLabel $feelsLikeTemp$degreeSign"

        showWeatherNotification(
            context = appContext,
            title = weatherTitle,
            body = weatherBody,
            weatherIconRes = LocalWeatherIcons.drawableResForOwmCode(iconCode),
            largeIcon = weatherIcon,
        )

        val alerts = mapAlerts(response.alerts)
        val notificationManager =
            appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (alerts.isEmpty()) {
            notificationManager.cancel(ALERT_NOTIFICATION_ID)
            return
        }

        val firstAlert = alerts.first()
        val remainingCount = alerts.size - 1
        val alertBody = buildString {
            if (firstAlert.description.isNotBlank()) {
                append(firstAlert.description.trim())
            }
            if (remainingCount > 0) {
                if (isNotEmpty()) append("\n\n")
                append(formatMoreAlerts(appContext, remainingCount))
            }
        }.ifBlank { firstAlert.headline }

        showAlertNotification(
            context = appContext,
            title = firstAlert.headline,
            body = alertBody,
        )
    }

    private fun showWeatherNotification(
        context: Context,
        title: String,
        body: String,
        weatherIconRes: Int,
        largeIcon: android.graphics.Bitmap?,
    ) {
        val pendingIntent = contentPendingIntent(context, WEATHER_NOTIFICATION_ID)
        val builder = NotificationCompat.Builder(context, WeatherNotificationHelper.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(weatherIconRes)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (largeIcon != null) {
            builder.setLargeIcon(largeIcon)
        } else {
            builder.setLargeIcon(
                androidx.core.content.ContextCompat.getDrawable(context, UI.drawable.logo)?.let { drawable ->
                    val bitmap = android.graphics.Bitmap.createBitmap(
                        drawable.intrinsicWidth.coerceAtLeast(1),
                        drawable.intrinsicHeight.coerceAtLeast(1),
                        android.graphics.Bitmap.Config.ARGB_8888,
                    )
                    val canvas = android.graphics.Canvas(bitmap)
                    drawable.setBounds(0, 0, canvas.width, canvas.height)
                    drawable.draw(canvas)
                    bitmap
                },
            )
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(WEATHER_NOTIFICATION_ID, builder.build())
    }

    private fun showAlertNotification(
        context: Context,
        title: String,
        body: String,
    ) {
        val pendingIntent = contentPendingIntent(context, ALERT_NOTIFICATION_ID)
        val notification = NotificationCompat.Builder(context, WeatherNotificationHelper.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(UI.drawable.logo)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(ALERT_NOTIFICATION_ID, notification)
    }

    private fun contentPendingIntent(context: Context, requestCode: Int): PendingIntent {
        val activityIntent = Intent(context, MainApplicationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        return PendingIntent.getActivity(
            context,
            requestCode,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
    }

    private fun formatMoreAlerts(context: Context, count: Int): String = when {
        count == 1 -> context.getString(R.string.notification_alerts_more_one)
        count in 2..4 -> context.getString(R.string.notification_alerts_more_few, count)
        else -> context.getString(R.string.notification_alerts_more, count)
    }
}
