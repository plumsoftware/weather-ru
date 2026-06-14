package ru.plumsoftware.weatherforecastru.material.materialui

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import ru.plumsoftware.weatherforecast.R

val InterFontFamily: FontFamily = FontFamily(
    Font(R.font.inter_18pt_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.inter_18pt_thinitalic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.inter_18pt_extralight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.inter_18pt_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.inter_18pt_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.inter_18pt_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.inter_18pt_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.inter_18pt_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.inter_18pt_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.inter_18pt_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.inter_18pt_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.inter_18pt_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.inter_18pt_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.inter_18pt_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.inter_18pt_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.inter_18pt_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.inter_18pt_black, FontWeight.Black, FontStyle.Normal),
    Font(R.font.inter_18pt_blackitalic, FontWeight.Black, FontStyle.Italic),
)

fun getLightFont(): FontFamily = InterFontFamily

fun getRegularFont(): FontFamily = InterFontFamily

fun getMediumFont(): FontFamily = InterFontFamily

fun getSemiBoldFont(): FontFamily = InterFontFamily

fun getBoldFont(): FontFamily = InterFontFamily
