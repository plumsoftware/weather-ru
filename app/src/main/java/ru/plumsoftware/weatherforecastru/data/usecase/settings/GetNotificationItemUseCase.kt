package ru.plumsoftware.weatherforecastru.data.usecase.settings

import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository
import ru.plumsoftware.weatherforecastru.data.models.settings.NotificationItem

class GetNotificationItemUseCase (private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun execute(): NotificationItem {
        val notificationItem: NotificationItem = sharedPreferencesRepository.getNotificationPeriod()
        return notificationItem
    }
}