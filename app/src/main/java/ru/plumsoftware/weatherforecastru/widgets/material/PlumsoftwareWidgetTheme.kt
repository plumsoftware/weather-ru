package ru.plumsoftware.weatherforecastru.widgets.material

import androidx.compose.ui.unit.sp
import androidx.glance.material3.ColorProviders
import androidx.glance.text.FontFamily
import androidx.glance.text.FontStyle
import androidx.glance.text.FontWeight
import androidx.glance.text.TextStyle
import ru.plumsoftware.weatherforecastru.presentation.ui.DarkColorScheme
import ru.plumsoftware.weatherforecastru.presentation.ui.LightColorScheme

object PlumsoftwareWidgetTheme {
    object PlumsoftwareWidgetColorScheme {
        val colors = ColorProviders(
            light = LightColorScheme,
            dark = DarkColorScheme
        )
    }

    object PlumsoftwareWidgetTypography {
        val regular = 14.sp
        val bold = 32.sp
        val medium = 24.sp
    }

    object PlumsoftwareWidgetStyle {
        val bold = TextStyle(
            fontSize = PlumsoftwareWidgetTypography.bold,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            fontFamily = FontFamily.SansSerif
        )
        val regular = TextStyle(
            fontSize = PlumsoftwareWidgetTypography.regular,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            fontFamily = FontFamily.SansSerif
        )
    }
}