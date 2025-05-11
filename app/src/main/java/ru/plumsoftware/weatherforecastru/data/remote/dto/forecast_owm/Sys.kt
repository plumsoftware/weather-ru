package ru.plumsoftware.weatherapp.weatherdata.forecast_owm

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod")
    @Expose
    val pod: String
)