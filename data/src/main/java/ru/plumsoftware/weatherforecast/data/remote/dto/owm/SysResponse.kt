package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Sys

@Serializable
data class SysResponse(
    @SerializedName("type") override var type: Int? = null,
    @SerializedName("id") override var id: Int? = null,
    @SerializedName("country") override var country: String? = null,
    @SerializedName("sunrise") override var sunrise: Int? = null,
    @SerializedName("sunset") override var sunset: Int? = null
) : Sys