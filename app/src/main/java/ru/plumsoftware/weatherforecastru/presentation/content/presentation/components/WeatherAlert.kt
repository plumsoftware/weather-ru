package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

data class WeatherAlert(
    val headline: String,
    val event: String,
    val severity: String,
    val urgency: String,
    val certainty: String,
    val description: String,
    val effective: String,
    val expires: String,
    val msgtype: String,
    val severityLevel: AlertSeverityLevel,
    val expiresAtMillis: Long?,
)

enum class AlertSeverityLevel(val rank: Int) {
    CRITICAL(4),
    HIGH(3),
    MEDIUM(2),
    LOW(1),
}
