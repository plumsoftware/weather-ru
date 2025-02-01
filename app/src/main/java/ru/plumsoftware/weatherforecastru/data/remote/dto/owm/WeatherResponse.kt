package ru.plumsoftware.weatherforecastru.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecastru.data.remote.owm.Weather

@Serializable
data class WeatherResponse(
    @SerializedName("id") override var id: Int? = 0,
    @SerializedName("main") override var main: String? = "",
    @SerializedName("description") override var description: String? = "",
    @SerializedName("icon") override var icon: String? = ""
) : Weather