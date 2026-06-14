package ru.plumsoftware.weatherforecastru.messanging

import android.app.job.JobScheduler
import android.content.Context
import android.os.Build
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import ru.plumsoftware.weatherforecastru.service.JOB_ID
import java.util.concurrent.TimeUnit

object WeatherNotificationScheduler {

    private const val UNIQUE_WORK_NAME = "weather_notification_periodic"

    fun schedule(context: Context) {
        cancelLegacyJobScheduler(context)

        val appContext = context.applicationContext
        val period = NotificationPeriods.normalize(
            WeatherNotificationDependencies
                .sharedPreferencesStorage(appContext)
                .getNotificationItem()
                .period,
        )

        val workManager = WorkManager.getInstance(appContext)
        if (period <= 0L) {
            workManager.cancelUniqueWork(UNIQUE_WORK_NAME)
            return
        }

        val (interval, unit) = intervalForPeriod(period)
        val request = PeriodicWorkRequestBuilder<WeatherNotificationWorker>(interval, unit)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build(),
            )
            .build()

        workManager.enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            request,
        )
    }

    private fun intervalForPeriod(period: Long): Pair<Long, TimeUnit> = when (period) {
        NotificationPeriods.THREE_HOURS -> 3L to TimeUnit.HOURS
        NotificationPeriods.TWELVE_HOURS -> 12L to TimeUnit.HOURS
        else -> 6L to TimeUnit.HOURS
    }

    private fun cancelLegacyJobScheduler(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
        runCatching {
            val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.cancel(JOB_ID)
        }
    }
}
