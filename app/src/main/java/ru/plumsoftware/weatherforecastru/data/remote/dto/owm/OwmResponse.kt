package ru.plumsoftware.weatherforecastru.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OwmResponse(
    @SerializedName("coord") var coord: CoordResponse? = CoordResponse(),
    @SerializedName("weather") var weather: ArrayList<WeatherResponse> = arrayListOf(WeatherResponse()),
    @SerializedName("base") var base: String? = "",
    @SerializedName("main") var main: MainResponse? = MainResponse(),
    @SerializedName("visibility") var visibility: Int? = 0,
    @SerializedName("wind") var wind: WindResponse? = WindResponse(),
    @SerializedName("rain") var rain: RainResponse? = RainResponse(),
    @SerializedName("clouds") var clouds: CloudsResponse? = CloudsResponse(),
    @SerializedName("dt") var dt: Int? = 0,
    @SerializedName("sys") var sys: SysResponse? = SysResponse(),
    @SerializedName("timezone") var timezone: Int? = 0,
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("name") var name: String? = "",
    @SerializedName("cod") var cod: Int? = 0
)
