package ru.plumsoftware.weatherapp.weatherdata.air_polutaon

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AirQualityItem(
    @SerializedName("main")
    @Expose
    val main: AirQualityMain,

    @SerializedName("components")
    @Expose
    val components: AirQualityComponents,

    @SerializedName("dt")
    @Expose
    val dt: Long
)