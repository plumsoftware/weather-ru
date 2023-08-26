package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Weather

@Serializable
data class WeatherResponse(
    override var id: Int? = null,
    override var main: String? = null,
    override var description: String? = null,
    override var icon: String? = null
) : Weather