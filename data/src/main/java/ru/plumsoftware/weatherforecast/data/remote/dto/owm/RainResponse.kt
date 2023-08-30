package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Rain

@Serializable
data class RainResponse(
    @SerializedName("1h") override var _1h: Double? = null
) : Rain