package ru.plumsoftware.weatherapp.weatherdata.air_polutaon

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AirQualityMain(
    @SerializedName("aqi")
    @Expose
    val aqi: Int
)