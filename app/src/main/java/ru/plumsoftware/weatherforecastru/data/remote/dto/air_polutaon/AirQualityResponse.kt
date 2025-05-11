package ru.plumsoftware.weatherforecastru.data.remote.dto.air_polutaon

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.plumsoftware.weatherapp.weatherdata.air_polutaon.AirQualityItem

data class AirQualityResponse(
    @SerializedName("coord")
    @Expose
    val coord: Coord = Coord(),

    @SerializedName("list")
    @Expose
    val list: List<AirQualityItem> = emptyList()
)