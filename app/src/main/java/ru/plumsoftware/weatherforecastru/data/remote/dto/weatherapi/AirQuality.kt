package ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi

import com.google.gson.annotations.SerializedName


data class AirQuality (

  @SerializedName("co"             ) var co             : Double?    = -1.0,
  @SerializedName("no2"            ) var no2            : Double? = -1.0,
  @SerializedName("o3"             ) var o3             : Double? = -1.0,
  @SerializedName("so2"            ) var so2            : Double? = -1.0,
  @SerializedName("pm2_5"          ) var pm25           : Double? = -1.0,
  @SerializedName("pm10"           ) var pm10           : Double? = -1.0,
  @SerializedName("us-epa-index"   ) var us_epa_index   : Double?    = -1.0,
  @SerializedName("gb-defra-index" ) var gb_defra_index : Double?    = -1.0

)