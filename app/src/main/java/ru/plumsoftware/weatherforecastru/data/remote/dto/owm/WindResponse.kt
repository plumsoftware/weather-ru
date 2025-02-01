package ru.plumsoftware.weatherforecastru.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecastru.data.remote.owm.Wind

@Serializable
data class WindResponse(
    @SerializedName("speed") override var speed: Double? = 0.0,
    @SerializedName("deg") override var deg: Int? = 0,
) : Wind