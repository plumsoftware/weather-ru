package ru.plumsoftware.weatherforecastru.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecastru.data.remote.owm.Rain

@Serializable
data class RainResponse(
    @SerializedName("1h") override var _1h: Double? = 0.0
) : Rain