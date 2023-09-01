package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Wind

@Serializable
data class WindResponse(
    @SerializedName("speed") override var speed: Double? = 0.0,
    @SerializedName("deg") override var deg: Int? = 0,
    @SerializedName("gust") override var gust: Double? = 0.0
) : Wind