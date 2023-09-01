package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Sys

@Serializable
data class SysResponse(
    @SerializedName("type") override var type: Int? = 0,
    @SerializedName("id") override var id: Int? = 0,
    @SerializedName("country") override var country: String? = "",
    @SerializedName("sunrise") override var sunrise: Int? = 0,
    @SerializedName("sunset") override var sunset: Int? = 0
) : Sys