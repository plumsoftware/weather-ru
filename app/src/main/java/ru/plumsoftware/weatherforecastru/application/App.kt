package ru.plumsoftware.weatherforecastru.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
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
import ru.plumsoftware.weatherforecastru.messanging.local.SimpleNotificationService

class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val sharedPreferencesRepository = SharedPreferencesRepositoryImpl(context = INSTANCE)
            val sharedPreferencesStorage = SharedPreferencesStorage(
                getUserSettingsUseCase = ru.plumsoftware.weatherforecastru.data.usecase.settings.GetUserSettingsUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                getUserSettingsShowTipsUseCase = GetUserSettingsShowTipsUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                getFirstUseCase = GetFirstUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveUserSettingsUseCase = SaveUserSettingsUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveUserSettingsAppThemeUseCase = SaveUserSettingsAppThemeUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveUserSettingsShowTipsUseCase = ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsShowTipsUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveUserSettingsWeatherUnitsUseCase = SaveUserSettingsWeatherUnitsUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveUserSettingsWindUnitsUseCase = SaveUserSettingsWindUnitsUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveUserSettingsLocationUseCase = ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsLocationUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveFirstUseCase = SaveFirstUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveWidgetConfigUseCase = SaveWidgetConfigUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                getWidgetConfigUseCase = GetWidgetConfigUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                getNotificationItemUseCase = GetNotificationItemUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveNotificationItemUseCase = SaveNotificationItemUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                )
            )

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
}
