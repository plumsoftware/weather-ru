package ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi

import com.google.gson.annotations.SerializedName

data class WeatherApiAstronomyResponse(
    @SerializedName("location") var location: Location? = null,
    @SerializedName("astronomy") var astronomy: AstronomyBlock? = null,
)

data class AstronomyBlock(
    @SerializedName("astro") var astro: Astro? = null,
)
