package ru.plumsoftware.weatherforecastru.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecastru.domain.remote.dto.owm.Coord

@Serializable
data class CoordResponse(
    @SerializedName("lon") override var lon: Double? = 0.0,
    @SerializedName("lat") override var lat: Double? = 0.0
) : Coord