package ru.plumsoftware.weatherforecastru.data.remote.dto.forecast_owm

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.plumsoftware.weatherapp.weatherdata.forecast_owm.WeatherItem

data class MainWeatherResponse(
    @SerializedName("cod")
    @Expose
    val cod: Int = 0,

    @SerializedName("message")
    @Expose
    val message: Int = 0,

    @SerializedName("cnt")
    @Expose
    val cnt: Int = 0,

    @SerializedName("list")
    @Expose
    val weatherList: List<WeatherItem> = emptyList(),

    @SerializedName("city")
    @Expose
    val city: City = City()
)