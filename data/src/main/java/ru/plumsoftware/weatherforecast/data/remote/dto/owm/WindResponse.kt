package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Wind

@Serializable
data class WindResponse(
    @SerializedName("speed") override var speed: Double? = null,
    @SerializedName("deg") override var deg: Int? = null,
    @SerializedName("gust") override var gust: Double? = null
) : Wind