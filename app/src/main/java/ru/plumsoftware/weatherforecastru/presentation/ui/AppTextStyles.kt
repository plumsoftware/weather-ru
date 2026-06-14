package ru.plumsoftware.weatherforecastru.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

object AppFontWeight {
    val Regular = FontWeight.Normal
    val Medium = FontWeight.Medium
    val SemiBold = FontWeight.SemiBold
    val Bold = FontWeight.Bold
}

fun TextStyle.regular(): TextStyle = copy(fontWeight = FontWeight.Normal)

fun TextStyle.medium(): TextStyle = copy(fontWeight = FontWeight.Medium)

fun TextStyle.semiBold(): TextStyle = copy(fontWeight = FontWeight.SemiBold)

fun TextStyle.bold(): TextStyle = copy(fontWeight = FontWeight.Bold)
