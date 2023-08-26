package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Main

@Serializable
data class MainResponse(
    override var temp: Double? = null,
    override var feelsLike: Double? = null,
    override var tempMin: Double? = null,
    override var tempMax: Double? = null,
    override var pressure: Int? = null,
    override var humidity: Int? = null,
    override var seaLevel: Int? = null,
    override var grndLevel: Int? = null
) : Main