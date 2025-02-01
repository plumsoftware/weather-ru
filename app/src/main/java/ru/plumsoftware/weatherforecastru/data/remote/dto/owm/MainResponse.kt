package ru.plumsoftware.weatherforecastru.data.remote.dto.owm

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecastru.data.remote.owm.Main

@Serializable
data class MainResponse(
    @SerializedName("temp") override var temp: Double? = 0.0,
    @SerializedName("feels_like") override var feelsLike: Double? = 0.0,
    @SerializedName("temp_min") override var tempMin: Double? = 0.0,
    @SerializedName("temp_max") override var tempMax: Double? = 0.0,
    @SerializedName("pressure") override var pressure: Int? = 0,
    @SerializedName("humidity") override var humidity: Int? = 0,
    @SerializedName("sea_level") override var seaLevel: Int? = 0,
    @SerializedName("grnd_level") override var grndLevel: Int? = 0
) : ru.plumsoftware.weatherforecastru.data.remote.owm.Main