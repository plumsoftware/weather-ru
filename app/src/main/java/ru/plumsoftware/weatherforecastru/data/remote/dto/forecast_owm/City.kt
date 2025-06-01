package ru.plumsoftware.weatherforecastru.data.remote.dto.forecast_owm

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id")
    @Expose
    val id: Int = 0,

    @SerializedName("name")
    @Expose
    val name: String = "",

    @SerializedName("coord")
    @Expose
    val coord: Coord = Coord(),

    @SerializedName("country")
    @Expose
    val country: String = "",

    @SerializedName("population")
    @Expose
    val population: Int = 0,

    @SerializedName("timezone")
    @Expose
    val timezone: Int = 0,

    @SerializedName("sunrise")
    @Expose
    val sunrise: Long = 0L,

    @SerializedName("sunset")
    @Expose
    val sunset: Long = 0L
)