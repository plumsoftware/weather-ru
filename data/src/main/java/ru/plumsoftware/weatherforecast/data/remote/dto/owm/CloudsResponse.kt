package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Clouds

@Serializable
data class CloudsResponse(
    @SerializedName("all") override var all: Int? = 0
) : Clouds