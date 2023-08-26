package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Clouds

@Serializable
data class CloudsResponse(
    override var all: Int? = null
) : Clouds