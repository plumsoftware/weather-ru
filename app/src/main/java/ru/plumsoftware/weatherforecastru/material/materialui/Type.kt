package ru.plumsoftware.weatherforecastru.material.materialui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionTypography

private val MaterialDefaults = Typography()
private val inter = InterFontFamily

private fun TextStyle.inter(): TextStyle = copy(fontFamily = inter)

internal val Typography = Typography(
    displayLarge = MaterialDefaults.displayLarge.inter(),
    displayMedium = MaterialDefaults.displayMedium.inter(),
    displaySmall = MaterialDefaults.displaySmall.inter(),
    headlineLarge = TextStyle(
        fontFamily = inter,
        fontWeight = ExtensionTypography.TypographyWeight.bold,
        fontSize = ExtensionTypography.TypographySize._20sp,
        lineHeight = ExtensionTypography.LineHeight.lineHeight,
        letterSpacing = ExtensionTypography.LetterSpacing.letterSpacing,
    ),
    headlineMedium = MaterialDefaults.headlineMedium.inter(),
    headlineSmall = MaterialDefaults.headlineSmall.inter(),
    titleLarge = TextStyle(
        fontFamily = inter,
        fontWeight = ExtensionTypography.TypographyWeight.bold,
        fontSize = ExtensionTypography.TypographySize._24sp,
        lineHeight = ExtensionTypography.LineHeight.lineHeight,
        letterSpacing = ExtensionTypography.LetterSpacing.letterSpacing,
    ),
    titleMedium = TextStyle(
        fontFamily = inter,
        fontWeight = ExtensionTypography.TypographyWeight.semi_bold,
        fontSize = ExtensionTypography.TypographySize._16sp,
        lineHeight = ExtensionTypography.LineHeight.lineHeight,
        letterSpacing = ExtensionTypography.LetterSpacing.letterSpacing,
    ),
    titleSmall = TextStyle(
        fontFamily = inter,
        fontWeight = ExtensionTypography.TypographyWeight.medium,
        fontSize = ExtensionTypography.TypographySize._20sp,
        lineHeight = ExtensionTypography.LineHeight.lineHeight,
        letterSpacing = ExtensionTypography.LetterSpacing.letterSpacing,
    ),
    bodyLarge = MaterialDefaults.bodyLarge.inter(),
    bodyMedium = TextStyle(
        fontFamily = inter,
        fontWeight = ExtensionTypography.TypographyWeight.regular,
        fontSize = ExtensionTypography.TypographySize._14sp,
        lineHeight = ExtensionTypography.LineHeight.lineHeight,
        letterSpacing = ExtensionTypography.LetterSpacing.letterSpacing,
    ),
    bodySmall = MaterialDefaults.bodySmall.inter(),
    labelLarge = TextStyle(
        fontFamily = inter,
        fontWeight = ExtensionTypography.TypographyWeight.regular,
        fontSize = ExtensionTypography.TypographySize._16sp,
        lineHeight = ExtensionTypography.LineHeight.lineHeight,
        letterSpacing = ExtensionTypography.LetterSpacing.letterSpacing,
    ),
    labelMedium = TextStyle(
        fontFamily = inter,
        fontWeight = ExtensionTypography.TypographyWeight.regular,
        fontSize = ExtensionTypography.TypographySize._16sp,
        lineHeight = ExtensionTypography.LineHeight.lineHeight,
        letterSpacing = ExtensionTypography.LetterSpacing.letterSpacing,
    ),
    labelSmall = TextStyle(
        fontFamily = inter,
        fontWeight = ExtensionTypography.TypographyWeight.light,
        fontSize = ExtensionTypography.TypographySize._12sp,
        lineHeight = ExtensionTypography.LineHeight.lineHeight,
        letterSpacing = ExtensionTypography.LetterSpacing.letterSpacing,
    ),
)
