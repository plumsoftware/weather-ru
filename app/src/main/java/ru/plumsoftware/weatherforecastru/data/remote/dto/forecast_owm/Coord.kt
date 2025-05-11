package ru.plumsoftware.weatherforecastru.data.remote.dto.forecast_owm

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat")
    @Expose
    val lat: Double = 0.0,

    @SerializedName("lon")
    @Expose
    val lon: Double = 0.0
)