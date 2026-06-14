package ru.plumsoftware.weatherforecastru.messanging

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class WeatherNotificationWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            WeatherNotificationSender.fetchAndShow(applicationContext)
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}
