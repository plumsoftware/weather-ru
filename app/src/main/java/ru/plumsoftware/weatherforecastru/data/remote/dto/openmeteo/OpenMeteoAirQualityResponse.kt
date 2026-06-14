package ru.plumsoftware.weatherforecastru.data.remote.dto.openmeteo

import com.google.gson.annotations.SerializedName

data class OpenMeteoAirQualityResponse(
    @SerializedName("current") val current: OpenMeteoCurrent? = null,
    @SerializedName("hourly") val hourly: OpenMeteoHourly? = null,
)

data class OpenMeteoCurrent(
    @SerializedName("european_aqi") val europeanAqi: Int? = null,
    @SerializedName("pm10") val pm10: Double? = null,
    @SerializedName("pm2_5") val pm25: Double? = null,
    @SerializedName("carbon_monoxide") val carbonMonoxide: Double? = null,
    @SerializedName("nitrogen_dioxide") val nitrogenDioxide: Double? = null,
    @SerializedName("ozone") val ozone: Double? = null,
    @SerializedName("sulphur_dioxide") val sulphurDioxide: Double? = null,
)

data class OpenMeteoHourly(
    @SerializedName("time") val time: List<String>? = null,
    @SerializedName("european_aqi") val europeanAqi: List<Int?>? = null,
)
