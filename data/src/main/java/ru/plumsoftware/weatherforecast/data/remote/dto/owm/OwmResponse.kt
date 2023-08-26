package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import kotlinx.serialization.Serializable

@Serializable
data class OwmResponse (
    var coord: CoordResponse? = CoordResponse(),
    var weather: ArrayList<WeatherResponse> = arrayListOf(),
    var base: String? = null,
    var main: MainResponse? = MainResponse(),
    var visibility: Int? = null,
    var wind: WindResponse? = WindResponse(),
    var rain: RainResponse? = RainResponse(),
    var clouds: CloudsResponse? = CloudsResponse(),
    var dt: Int? = null,
    var sys: SysResponse? = SysResponse(),
    var timezone: Int? = null,
    var id: Int? = null,
    var name: String? = null,
    var cod: Int? = null
)
