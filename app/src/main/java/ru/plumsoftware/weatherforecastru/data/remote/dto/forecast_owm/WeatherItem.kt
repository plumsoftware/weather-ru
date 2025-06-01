package ru.plumsoftware.weatherapp.weatherdata.forecast_owm

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.plumsoftware.weatherforecastru.data.remote.dto.forecast_owm.Rain

data class WeatherItem(
    @SerializedName("dt")
    @Expose
    val dt: Long,

    @SerializedName("main")
    @Expose
    val main: MainInfo,

    @SerializedName("weather")
    @Expose
    val weather: List<WeatherDescription>,

    @SerializedName("clouds")
    @Expose
    val clouds: Clouds,

    @SerializedName("wind")
    @Expose
    val wind: Wind,

    @SerializedName("visibility")
    @Expose
    val visibility: Int,

    @SerializedName("pop")
    @Expose
    val pop: Double,

    @SerializedName("sys")
    @Expose
    val sys: Sys,

    @SerializedName("dt_txt")
    @Expose
    val dtTxt: String,

    @SerializedName("rain")
    @Expose
    val rain: Rain? = null
)