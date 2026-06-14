package ru.plumsoftware.weatherforecastru.data.models.airquality

data class AirQualityData(
    val aqi: Int = 0,
    val aqiLabel: String = "",
    val aqiDescription: String = "",
    val pollutants: List<Pollutant> = emptyList(),
    val forecast: List<AqiForecastDay> = emptyList(),
)

data class Pollutant(
    val name: String,
    val fullName: String,
    val value: Float,
    val unit: String,
    val normalMax: Float,
    val ratio: Float = (value / normalMax).coerceIn(0f, 1.5f),
)

data class AqiForecastDay(
    val label: String,
    val aqi: Int,
    val aqiLabel: String,
)

fun aqiLabel(aqi: Int): String = when {
    aqi <= 20 -> "Отличное"
    aqi <= 40 -> "Хорошее"
    aqi <= 60 -> "Умеренное"
    aqi <= 80 -> "Плохое"
    aqi <= 100 -> "Очень плохое"
    else -> "Опасное"
}

fun aqiShortLabel(aqi: Int): String = when {
    aqi <= 0 -> "—"
    aqi <= 50 -> "Хор."
    aqi <= 100 -> "Умер."
    aqi <= 150 -> "Вред."
    else -> "Оч.вр."
}

fun aqiDescription(aqi: Int): String = when {
    aqi <= 40 -> "Воздух чистый, идеально для прогулок"
    aqi <= 60 -> "Приемлемое качество для большинства людей"
    aqi <= 80 -> "Чувствительным людям рекомендуется сократить время на улице"
    else -> "Рекомендуется оставаться дома и проветривать помещение"
}
