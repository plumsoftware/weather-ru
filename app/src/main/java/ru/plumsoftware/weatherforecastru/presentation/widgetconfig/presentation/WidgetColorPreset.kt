package ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation

import androidx.compose.ui.graphics.Color

data class WidgetColorPreset(
    val nameResId: Int,
    val red: Int,
    val green: Int,
    val blue: Int,
) {
    val color: Color get() = Color(red, green, blue)
}
