package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import kotlinx.serialization.Serializable

@Serializable
data class RainResponse(
    var _1h: Double? = null
)