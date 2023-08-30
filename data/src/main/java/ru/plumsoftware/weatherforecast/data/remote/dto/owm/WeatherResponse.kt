package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Weather

@Serializable
data class WeatherResponse(
    @SerializedName("id") override var id: Int? = null,
    @SerializedName("main") override var main: String? = null,
    @SerializedName("description") override var description: String? = null,
    @SerializedName("icon") override var icon: String? = null
) : Weather