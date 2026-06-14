package ru.plumsoftware.weatherforecastru.messanging

import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.data.models.settings.NotificationItem

object NotificationPeriods {
    const val THREE_HOURS = 10800000L
    const val SIX_HOURS = 21600000L
    const val TWELVE_HOURS = 43200000L

    fun normalize(period: Long): Long = when (period) {
        THREE_HOURS, SIX_HOURS, TWELVE_HOURS -> period
        else -> SIX_HOURS
    }

    fun indexForPeriod(period: Long): Int = when (normalize(period)) {
        THREE_HOURS -> 0
        TWELVE_HOURS -> 2
        else -> 1
    }

    fun itemForIndex(index: Int): NotificationItem = when (index) {
        0 -> NotificationItem(R.string.every_three_hours, THREE_HOURS)
        2 -> NotificationItem(R.string.every_twelve_hours, TWELVE_HOURS)
        else -> NotificationItem(R.string.every_six_hours, SIX_HOURS)
    }
}
