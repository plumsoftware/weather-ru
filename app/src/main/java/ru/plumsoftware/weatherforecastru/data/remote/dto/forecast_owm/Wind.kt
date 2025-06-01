package ru.plumsoftware.weatherapp.weatherdata.forecast_owm

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    @Expose
    val speed: Double,

    @SerializedName("deg")
    @Expose
    val deg: Int,

    @SerializedName("gust")
    @Expose
    val gust: Double
)