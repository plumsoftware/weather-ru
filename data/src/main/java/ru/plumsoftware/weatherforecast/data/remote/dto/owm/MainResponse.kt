package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Main

@Serializable
data class MainResponse(
    @SerializedName("temp") override var temp: Double? = null,
    @SerializedName("feels_like") override var feelsLike: Double? = null,
    @SerializedName("temp_min") override var tempMin: Double? = null,
    @SerializedName("temp_max") override var tempMax: Double? = null,
    @SerializedName("pressure") override var pressure: Int? = null,
    @SerializedName("humidity") override var humidity: Int? = null,
    @SerializedName("sea_level") override var seaLevel: Int? = null,
    @SerializedName("grnd_level") override var grndLevel: Int? = null
) : Main