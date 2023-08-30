package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Coord

@Serializable
data class CoordResponse(
    @SerializedName("lon") override var lon: Double? = null,
    @SerializedName("lat") override var lat: Double? = null
) : Coord