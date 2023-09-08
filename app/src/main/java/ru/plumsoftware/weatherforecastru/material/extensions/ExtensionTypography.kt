package ru.plumsoftware.weatherforecastru.material.extensions

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal object ExtensionTypography {
    object TypographySize {
        val _24sp = 24.sp
        val _20sp = 20.sp
        val _16sp = 16.sp
        val _15sp = 15.sp
        val _14sp = 14.sp
        val _12sp = 12.sp
    }

    object TypographyWeight {
        val bold = FontWeight(700)
        val semi_bold = FontWeight(600)
        val medium = FontWeight(500)
        val regular = FontWeight(400)
        val light = FontWeight(300)
    }

    object LetterSpacing {
        val letterSpacing = 0.04.sp
    }

    object LineHeight {
        val lineHeight = 20.sp
    }
}