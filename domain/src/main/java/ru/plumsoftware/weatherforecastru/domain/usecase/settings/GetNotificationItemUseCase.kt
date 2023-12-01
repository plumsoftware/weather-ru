package ru.plumsoftware.weatherforecastru.domain.usecase.settings

import ru.plumsoftware.weatherforecastru.domain.models.settings.NotificationItem
import ru.plumsoftware.weatherforecastru.domain.repository.SharedPreferencesRepository

class GetNotificationItemUseCase (private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(): NotificationItem {
        val notificationItem: NotificationItem = sharedPreferencesRepository.getNotificationPeriod()
        return notificationItem
    }
}