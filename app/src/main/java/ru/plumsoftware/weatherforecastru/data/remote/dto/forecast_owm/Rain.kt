package ru.plumsoftware.weatherforecastru.data.remote.dto.forecast_owm

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    @Expose
    val h3: Double
)