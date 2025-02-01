package ru.plumsoftware.weatherforecastru.data.usecase.settings

import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository
import ru.plumsoftware.weatherforecastru.data.models.settings.NotificationItem

class SaveNotificationItemUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(notificationItem: NotificationItem) {
        sharedPreferencesRepository.saveNotificationPeriod(notificationItem = notificationItem)
    }
}