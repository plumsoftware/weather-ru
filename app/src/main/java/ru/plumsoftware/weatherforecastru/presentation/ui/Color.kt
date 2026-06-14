package ru.plumsoftware.weatherforecastru.presentation.ui

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ═══════════════════════════════════════════
// СВЕТЛАЯ ТЕМА
// ═══════════════════════════════════════════
val BackgroundLight = Color(0xFFEDF2FB)
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceVariantLight = Color(0xFFE8EDF8)
val SurfaceContainerLight = Color(0xFFF2F5FD)

val PrimaryLight = Color(0xFF3D6FE8)
val OnPrimaryLight = Color.White
val PrimaryContainerLight = Color(0xFFD8E4FF)
val OnPrimaryContainerLight = Color(0xFF001357)

val OnBackgroundLight = Color(0xFF1A1E2E)
val OnSurfaceLight = Color(0xFF1A1E2E)
val OnSurfaceVariantLight = Color(0xFF5A6072)
val OutlineLight = Color(0xFFD0D8EE)
val OutlineVariantLight = Color(0xFFE8EDF8)

val TempHotLight = Color(0xFFE85B3D)
val TempWarmLight = Color(0xFFF5A623)
val TempCoolLight = Color(0xFF3D6FE8)
val TempColdLight = Color(0xFF7B9CD4)

val AqiGoodLight = Color(0xFF27A85A)
val AqiModerateLight = Color(0xFFF5A623)
val AqiUnhealthyLight = Color(0xFFE85B3D)
val AqiVeryUnhealthyLight = Color(0xFF9B27AF)

// ═══════════════════════════════════════════
// ТЁМНАЯ ТЕМА
// ═══════════════════════════════════════════
val BackgroundDark = Color(0xFF0B0F1A)
val SurfaceDark = Color(0xFF151A2E)
val SurfaceVariantDark = Color(0xFF1E2540)
val SurfaceContainerDark = Color(0xFF1A2038)

val PrimaryDark = Color(0xFF6B9AFF)
val OnPrimaryDark = Color(0xFF001357)
val PrimaryContainerDark = Color(0xFF1C3575)
val OnPrimaryContainerDark = Color(0xFFD8E4FF)

val OnBackgroundDark = Color(0xFFE8EEFF)
val OnSurfaceDark = Color(0xFFE8EEFF)
val OnSurfaceVariantDark = Color(0xFF8A9BBD)
val OutlineDark = Color(0xFF2A3455)
val OutlineVariantDark = Color(0xFF1E2845)

val TempHotDark = Color(0xFFFF8C6B)
val TempWarmDark = Color(0xFFFFBD4A)
val TempCoolDark = Color(0xFF6B9AFF)
val TempColdDark = Color(0xFF9DB8E8)

val AqiGoodDark = Color(0xFF4EC88A)
val AqiModerateDark = Color(0xFFFFBD4A)
val AqiUnhealthyDark = Color(0xFFFF8C6B)
val AqiVeryUnhealthyDark = Color(0xFFCF6FE8)

// Алиасы для XML / виджетов / legacy-кода
val md_theme_light_primary = PrimaryLight
val md_theme_light_onPrimary = OnPrimaryLight
val md_theme_light_primaryContainer = PrimaryContainerLight
val md_theme_light_onPrimaryContainer = OnPrimaryContainerLight
val md_theme_light_secondary = OnSurfaceVariantLight
val md_theme_light_onSecondary = Color.White
val md_theme_light_secondaryContainer = SurfaceVariantLight
val md_theme_light_onSecondaryContainer = OnBackgroundLight
val md_theme_light_tertiary = AqiVeryUnhealthyLight
val md_theme_light_onTertiary = Color.White
val md_theme_light_tertiaryContainer = Color(0xFFF3E5F5)
val md_theme_light_onTertiaryContainer = OnBackgroundLight
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color.White
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = BackgroundLight
val md_theme_light_onBackground = OnBackgroundLight
val md_theme_light_surface = SurfaceLight
val md_theme_light_onSurface = OnSurfaceLight
val md_theme_light_surfaceVariant = SurfaceVariantLight
val md_theme_light_onSurfaceVariant = OnSurfaceVariantLight
val md_theme_light_outline = OutlineLight
val md_theme_light_inverseOnSurface = OnBackgroundLight
val md_theme_light_inverseSurface = OnBackgroundLight
val md_theme_light_inversePrimary = PrimaryLight
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = PrimaryLight
val md_theme_light_outlineVariant = OutlineVariantLight
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = PrimaryDark
val md_theme_dark_onPrimary = OnPrimaryDark
val md_theme_dark_primaryContainer = PrimaryContainerDark
val md_theme_dark_onPrimaryContainer = OnPrimaryContainerDark
val md_theme_dark_secondary = OnSurfaceVariantDark
val md_theme_dark_onSecondary = OnBackgroundDark
val md_theme_dark_secondaryContainer = SurfaceVariantDark
val md_theme_dark_onSecondaryContainer = OnBackgroundDark
val md_theme_dark_tertiary = AqiVeryUnhealthyDark
val md_theme_dark_onTertiary = OnBackgroundDark
val md_theme_dark_tertiaryContainer = SurfaceVariantDark
val md_theme_dark_onTertiaryContainer = OnBackgroundDark
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = BackgroundDark
val md_theme_dark_onBackground = OnBackgroundDark
val md_theme_dark_surface = SurfaceDark
val md_theme_dark_onSurface = OnSurfaceDark
val md_theme_dark_surfaceVariant = SurfaceVariantDark
val md_theme_dark_onSurfaceVariant = OnSurfaceVariantDark
val md_theme_dark_outline = OutlineDark
val md_theme_dark_inverseOnSurface = OnBackgroundDark
val md_theme_dark_inverseSurface = OnBackgroundDark
val md_theme_dark_inversePrimary = PrimaryDark
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = PrimaryDark
val md_theme_dark_outlineVariant = OutlineVariantDark
val md_theme_dark_scrim = Color(0xFF000000)

val seed = PrimaryLight

internal val md_theme_light_bar = Color(0xFF00FFB2)
internal val md_theme_dark_bar = Color(0xFF08E7A4)
internal val md_theme_icon_tint = Color(0x33000000)
internal val md_theme_icon_tint_2 = Color(0x66000000)
internal val md_theme_orange = TempWarmLight
internal val md_theme_yellow = TempWarmLight
internal val md_theme_blue = PrimaryLight
internal val md_theme_cyan = TempColdLight
internal val md_theme_purple = AqiVeryUnhealthyLight
internal val md_theme_pink = AqiUnhealthyLight
internal val md_theme_violet = AqiVeryUnhealthyLight
internal val md_theme_dark_yellow = TempWarmDark
internal val md_theme_light_weather_bar = Color(0xFF00FFB2)
internal val md_theme_dark_weather_bar = Color(0xFF08E7A4)
internal val md_theme_gray = SurfaceVariantLight
internal val md_theme_gray_2 = OnSurfaceVariantLight
internal val md_theme_light_gray = OutlineLight
internal val md_theme_dark_gray = OnSurfaceVariantDark
internal val md_theme_sunny_color = TempWarmLight
internal val md_theme_visibility_color = AqiUnhealthyLight
internal val md_theme_wind_color = TempWarmLight
internal val md_theme_humidity_color = PrimaryLight
internal val md_theme_tint_cover = Color(0x33FFFFFF)
internal val md_theme_text_cover = OnSurfaceVariantLight
internal val md_theme_moon_phase = TempColdLight
internal val md_theme_text_hour_cover = Color(0x663D6FE8)

val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = OnSurfaceVariantLight,
    onSecondary = Color.White,
    secondaryContainer = SurfaceVariantLight,
    onSecondaryContainer = OnBackgroundLight,
    tertiary = AqiVeryUnhealthyLight,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFF3E5F5),
    onTertiaryContainer = OnBackgroundLight,
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    onError = Color.White,
    onErrorContainer = Color(0xFF410002),
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    inverseOnSurface = OnBackgroundLight,
    inverseSurface = OnBackgroundLight,
    inversePrimary = PrimaryLight,
    surfaceTint = PrimaryLight,
    scrim = Color(0xFF000000),
)

val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = OnSurfaceVariantDark,
    onSecondary = OnBackgroundDark,
    secondaryContainer = SurfaceVariantDark,
    onSecondaryContainer = OnBackgroundDark,
    tertiary = AqiVeryUnhealthyDark,
    onTertiary = OnBackgroundDark,
    tertiaryContainer = SurfaceVariantDark,
    onTertiaryContainer = OnBackgroundDark,
    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF93000A),
    onError = Color(0xFF690005),
    onErrorContainer = Color(0xFFFFDAD6),
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    inverseOnSurface = OnBackgroundDark,
    inverseSurface = OnBackgroundDark,
    inversePrimary = PrimaryDark,
    surfaceTint = PrimaryDark,
    scrim = Color(0xFF000000),
)
