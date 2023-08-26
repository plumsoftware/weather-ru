package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Coord

@Serializable
data class CoordResponse(
     override var lon: Double? = null,
     override var lat: Double? = null
) : Coord