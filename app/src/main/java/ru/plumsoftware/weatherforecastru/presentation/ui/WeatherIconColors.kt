package ru.plumsoftware.weatherforecastru.presentation.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun humidityIconColor(): Color =
    if (isSystemInDarkTheme()) PrimaryDark else PrimaryLight

@Composable
fun windIconColor(): Color =
    if (isSystemInDarkTheme()) TempWarmDark else Color(0xFFE8913A)

@Composable
fun visibilityIconColor(): Color =
    if (isSystemInDarkTheme()) Color(0xFFD4956A) else Color(0xFFB86B42)

@Composable
fun sunnyAccentColor(): Color =
    if (isSystemInDarkTheme()) TempWarmDark else TempWarmLight
