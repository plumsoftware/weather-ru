package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Alert
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Alerts
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun mapAlerts(
    alerts: Alerts?,
    nowMillis: Long = System.currentTimeMillis(),
): List<WeatherAlert> {
    return alerts?.alert.orEmpty()
        .mapNotNull(::mapAlert)
        .filter { alert ->
            val expiresAt = alert.expiresAtMillis
            expiresAt == null || expiresAt > nowMillis
        }
        .filter { alert -> alert.msgtype.lowercase() != "cancel" }
        .distinctBy { alert -> "${alert.event}|${alert.expires}" }
        .sortedByDescending { alert -> alert.severityLevel.rank }
}

private fun mapAlert(dto: Alert): WeatherAlert? {
    val headline = dto.headline?.trim().orEmpty()
    val event = dto.event?.trim().orEmpty()
    if (headline.isEmpty() && event.isEmpty()) return null

    val expires = dto.expires.orEmpty()
    return WeatherAlert(
        headline = headline.ifBlank { event },
        event = event.ifBlank { headline },
        severity = dto.severity.orEmpty(),
        urgency = dto.urgency.orEmpty(),
        certainty = dto.certainty.orEmpty(),
        description = dto.desc.orEmpty(),
        effective = dto.effective.orEmpty(),
        expires = expires,
        msgtype = dto.msgtype.orEmpty(),
        severityLevel = mapAlertSeverity(dto.severity),
        expiresAtMillis = parseAlertIsoDate(expires),
    )
}

fun mapAlertSeverity(severity: String?): AlertSeverityLevel = when (severity?.trim()?.lowercase()) {
    "extreme" -> AlertSeverityLevel.CRITICAL
    "severe" -> AlertSeverityLevel.HIGH
    "moderate" -> AlertSeverityLevel.MEDIUM
    "minor" -> AlertSeverityLevel.LOW
    else -> AlertSeverityLevel.LOW
}

fun parseAlertIsoDate(iso: String?): Long? {
    if (iso.isNullOrBlank()) return null
    return runCatching {
        OffsetDateTime.parse(iso, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .toInstant()
            .toEpochMilli()
    }.getOrElse {
        runCatching {
            val normalized = iso.replace(Regex("([+-]\\d{2})(\\d{2})$"), "$1:$2")
            OffsetDateTime.parse(normalized, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                .toInstant()
                .toEpochMilli()
        }.getOrNull()
    }
}

fun formatAlertExpires(iso: String?, expiresAtMillis: Long?): String? {
    if (expiresAtMillis == null) return null
    return runCatching {
        val dateTime = java.time.Instant.ofEpochMilli(expiresAtMillis)
            .atZone(java.time.ZoneId.systemDefault())
        DateTimeFormatter.ofPattern("d MMM HH:mm", java.util.Locale.getDefault())
            .format(dateTime)
    }.getOrNull() ?: iso?.takeIf { it.isNotBlank() }
}
