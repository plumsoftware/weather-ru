package ru.plumsoftware.weatherapp.weatherdata.forecast_owm

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MainInfo(
    @SerializedName("temp")
    @Expose
    val temp: Double,

    @SerializedName("feels_like")
    @Expose
    val feelsLike: Double,

    @SerializedName("temp_min")
    @Expose
    val tempMin: Double,

    @SerializedName("temp_max")
    @Expose
    val tempMax: Double,

    @SerializedName("pressure")
    @Expose
    val pressure: Int,

    @SerializedName("sea_level")
    @Expose
    val seaLevel: Int,

    @SerializedName("grnd_level")
    @Expose
    val grndLevel: Int,

    @SerializedName("humidity")
    @Expose
    val humidity: Int,

    @SerializedName("temp_kf")
    @Expose
    val tempKf: Double
)