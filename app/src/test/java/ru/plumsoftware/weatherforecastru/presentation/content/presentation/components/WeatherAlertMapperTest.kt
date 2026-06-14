package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Alert
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Alerts
import java.time.OffsetDateTime
import java.time.ZoneOffset

class WeatherAlertMapperTest {

    @Test
    fun mapAlerts_filtersExpiredAndCancelledAndDeduplicates() {
        val future = OffsetDateTime.now(ZoneOffset.UTC).plusHours(6).toString()
        val past = OffsetDateTime.now(ZoneOffset.UTC).minusHours(1).toString()

        val alerts = mapAlerts(
            alerts = Alerts(
                alert = arrayListOf(
                    alert(
                        headline = "Active alert",
                        event = "Fire danger",
                        severity = "Severe",
                        expires = future,
                    ),
                    alert(
                        headline = "Expired alert",
                        event = "Expired",
                        severity = "Moderate",
                        expires = past,
                    ),
                    alert(
                        headline = "Cancelled alert",
                        event = "Cancelled",
                        severity = "Minor",
                        expires = future,
                        msgtype = "Cancel",
                    ),
                    alert(
                        headline = "Duplicate alert",
                        event = "Fire danger",
                        severity = "Severe",
                        expires = future,
                    ),
                ),
            ),
            nowMillis = System.currentTimeMillis(),
        )

        assertEquals(1, alerts.size)
        assertEquals("Active alert", alerts.first().headline)
        assertEquals(AlertSeverityLevel.HIGH, alerts.first().severityLevel)
    }

    @Test
    fun mapAlerts_sortsBySeverityDesc() {
        val future = OffsetDateTime.now(ZoneOffset.UTC).plusDays(1).toString()
        val alerts = mapAlerts(
            alerts = Alerts(
                alert = arrayListOf(
                    alert(headline = "Low", event = "Low", severity = "Minor", expires = future),
                    alert(headline = "Critical", event = "Critical", severity = "Extreme", expires = future),
                    alert(headline = "Medium", event = "Medium", severity = "Moderate", expires = future),
                    alert(headline = "High", event = "High", severity = "Severe", expires = future),
                    alert(headline = "Another low", event = "Another low", severity = "Minor", expires = future),
                ),
            ),
        )

        assertEquals(5, alerts.size)
        assertEquals(AlertSeverityLevel.CRITICAL, alerts[0].severityLevel)
        assertEquals(AlertSeverityLevel.HIGH, alerts[1].severityLevel)
        assertEquals(AlertSeverityLevel.MEDIUM, alerts[2].severityLevel)
        assertEquals(AlertSeverityLevel.LOW, alerts[3].severityLevel)
        assertEquals(AlertSeverityLevel.LOW, alerts[4].severityLevel)
    }

    @Test
    fun mapAlertSeverity_mapsKnownValues() {
        assertEquals(AlertSeverityLevel.CRITICAL, mapAlertSeverity("Extreme"))
        assertEquals(AlertSeverityLevel.HIGH, mapAlertSeverity("Severe"))
        assertEquals(AlertSeverityLevel.MEDIUM, mapAlertSeverity("Moderate"))
        assertEquals(AlertSeverityLevel.LOW, mapAlertSeverity("Minor"))
        assertEquals(AlertSeverityLevel.LOW, mapAlertSeverity(null))
    }

    @Test
    fun parseAlertIsoDate_parsesOffsetDateTime() {
        val millis = parseAlertIsoDate("2026-06-14T19:00:00+03:00")
        assertTrue(millis != null)
    }

    private fun alert(
        headline: String,
        event: String,
        severity: String,
        expires: String,
        msgtype: String = "Alert",
    ): Alert = Alert(
        headline = headline,
        event = event,
        severity = severity,
        expires = expires,
        msgtype = msgtype,
        desc = "Description for $headline",
    )
}
