package ru.plumsoftware.weatherforecastru.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetFirstUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetNotificationItemUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetUserSettingsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveFirstUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveNotificationItemUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsAppThemeUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsLocationUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsWeatherUnitsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsWindUnitsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.widget.GetWidgetConfigUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.widget.SaveWidgetConfigUseCase
import ru.plumsoftware.weatherforecastru.messanging.WeatherNotificationHelper
import ru.plumsoftware.weatherforecastru.messanging.WeatherNotificationScheduler

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        ru.plumsoftware.weatherforecastru.data.map.OsmdroidInitializer.initialize(this)
        ru.plumsoftware.weatherforecastru.data.weather.WeatherIconImageLoader.initialize(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        WeatherNotificationScheduler.schedule(context = this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            WeatherNotificationHelper.NOTIFICATION_CHANNEL_ID,
            getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = getString(R.string.notification_channel_description)
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
