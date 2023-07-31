package ru.plumsoftware.weatherforecast.material.materialui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionTypography


internal val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = getSemiBoldFont(),
        fontWeight = ExtensionTypography.TypographyWeight.bold,
        fontSize = ExtensionTypography.TypographySize._24sp,
        lineHeight = ExtensionTypography.LineHeight.lineHeight,
        letterSpacing = ExtensionTypography.LetterSpacing.letterSpacing
    ),
    titleMedium = TextStyle(
        fontFamily = getSemiBoldFont(),
        fontWeight = ExtensionTypography.TypographyWeight.semi_bold,
        fontSize = ExtensionTypography.TypographySize._16sp,
        lineHeight = ExtensionTypography.LineHeight.lineHeight,
        letterSpacing = ExtensionTypography.LetterSpacing.letterSpacing
    ),
    titleSmall = TextStyle(
        fontFamily = getMediumFont(),
        fontWeight = ExtensionTypography.TypographyWeight.medium,
        fontSize = ExtensionTypography.TypographySize._20sp,
        lineHeight = ExtensionTypography.LineHeight.lineHeight,
        letterSpacing = ExtensionTypography.LetterSpacing.letterSpacing
    ),

    headlineLarge = TextStyle(
        fontFamily = getBoldFont(),
        fontWeight = ExtensionTypography.TypographyWeight.bold,
        fontSize = ExtensionTypography.TypographySize._20sp,
        lineHeight = ExtensionTypography.LineHeight.lineHeight,
        letterSpacing = ExtensionTypography.LetterSpacing.letterSpacing
    ),

    labelMedium = TextStyle(
        fontFamily = getRegularFont(),
        fontWeight = ExtensionTypography.TypographyWeight.regular,
        fontSize = ExtensionTypography.TypographySize._16sp,
        lineHeight = ExtensionTypography.LineHeight.lineHeight,
        letterSpacing = ExtensionTypography.LetterSpacing.letterSpacing
    ),
    labelSmall = TextStyle(
        fontFamily = getLightFont(),
        fontWeight = ExtensionTypography.TypographyWeight.light,
        fontSize = ExtensionTypography.TypographySize._12sp,
        lineHeight = ExtensionTypography.LineHeight.lineHeight,
        letterSpacing = ExtensionTypography.LetterSpacing.letterSpacing,
    )
)