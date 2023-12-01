package ru.plumsoftware.weatherforecastru.domain.usecase.settings

import ru.plumsoftware.weatherforecastru.domain.models.settings.NotificationItem
import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

class SaveNotificationItemUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(notificationItem: NotificationItem) {
        sharedPreferencesRepository.saveNotificationPeriod(notificationItem = notificationItem)
    }
}