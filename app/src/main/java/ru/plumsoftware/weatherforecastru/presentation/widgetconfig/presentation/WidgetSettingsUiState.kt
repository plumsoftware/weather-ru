package ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation

import androidx.compose.ui.graphics.Color
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.store.WidgetConfigStore

data class WidgetSettingsUiState(
    val radius: Float = 22f,
    val red: Float = 255f,
    val green: Float = 255f,
    val blue: Float = 255f,
    val opacity: Float = 1f,
) {
    val backgroundColor: Color
        get() = Color(red / 255f, green / 255f, blue / 255f)

    val contentColor: Color
        get() = calculateContentColor(backgroundColor)
}

fun WidgetConfigStore.State.toUiState(): WidgetSettingsUiState =
    WidgetSettingsUiState(
        radius = radius.toFloat(),
        red = red.toFloat(),
        green = green.toFloat(),
        blue = blue.toFloat(),
        opacity = opacity,
    )

fun calculateContentColor(backgroundColor: Color): Color {
    val luminance = 0.299 * backgroundColor.red +
        0.587 * backgroundColor.green +
        0.114 * backgroundColor.blue
    return if (luminance > 0.5f) Color(0xFF1A1E2E) else Color.White
}
