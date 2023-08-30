package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OwmResponse(
    @SerializedName("coord") var coord: CoordResponse? = CoordResponse(),
    @SerializedName("weather") var weather: ArrayList<WeatherResponse> = arrayListOf(),
    @SerializedName("base") var base: String? = null,
    @SerializedName("main") var main: MainResponse? = MainResponse(),
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("wind") var wind: WindResponse? = WindResponse(),
    @SerializedName("rain") var rain: RainResponse? = RainResponse(),
    @SerializedName("clouds") var clouds: CloudsResponse? = CloudsResponse(),
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("sys") var sys: SysResponse? = SysResponse(),
    @SerializedName("timezone") var timezone: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("cod") var cod: Int? = null
)
