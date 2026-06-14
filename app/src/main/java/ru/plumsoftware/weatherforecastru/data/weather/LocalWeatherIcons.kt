package ru.plumsoftware.weatherforecastru.data.weather

import ru.plumsoftware.weatherforecast.R

object LocalWeatherIcons {
    private val owmCodeToDrawable = mapOf(
        "01d" to R.drawable.wicon_00,
        "01n" to R.drawable.wicon_01,
        "02d" to R.drawable.wicon_03,
        "02n" to R.drawable.wicon_04,
        "03d" to R.drawable.wicon_05,
        "03n" to R.drawable.wicon_06,
        "04d" to R.drawable.wicon_07,
        "04n" to R.drawable.wicon_07,
        "09d" to R.drawable.wicon_09,
        "09n" to R.drawable.wicon_15,
        "10d" to R.drawable.wicon_08,
        "10n" to R.drawable.wicon_14,
        "10d_l" to R.drawable.wicon_08,
        "10n_l" to R.drawable.wicon_14,
        "10d_h" to R.drawable.wicon_10,
        "10n_h" to R.drawable.wicon_16,
        "10d_s" to R.drawable.wicon_09,
        "10n_s" to R.drawable.wicon_15,
        "11d" to R.drawable.wicon_11,
        "11n" to R.drawable.wicon_17,
        "11d_l" to R.drawable.wicon_11,
        "11n_l" to R.drawable.wicon_17,
        "11d_h" to R.drawable.wicon_13,
        "11n_h" to R.drawable.wicon_19,
        "11d_s" to R.drawable.wicon_12,
        "11n_s" to R.drawable.wicon_18,
        "13d" to R.drawable.wicon_22,
        "13n" to R.drawable.wicon_22,
        "50d" to R.drawable.wicon_07,
        "50n" to R.drawable.wicon_07,
        "c_r_l" to R.drawable.wicon_20,
        "c_r_s" to R.drawable.wicon_21,
        "c_r_h" to R.drawable.wicon_22,
        "c_t_l" to R.drawable.wicon_23,
        "c_t_s" to R.drawable.wicon_24,
        "c_t_h" to R.drawable.wicon_25,
    )

    fun drawableResForMoonPhase(@Suppress("UNUSED_PARAMETER") moonPhase: String): Int =
        R.drawable.detail_moon

    fun drawableResForOwmCode(iconCode: String): Int {
        val normalized = iconCode.trim().lowercase()
        return owmCodeToDrawable[normalized] ?: if (normalized.endsWith("n")) {
            R.drawable.wicon_06
        } else {
            R.drawable.wicon_05
        }
    }
}
