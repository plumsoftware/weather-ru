package ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi

import com.google.gson.annotations.SerializedName


data class Astro (

  @SerializedName("sunrise"           ) var sunrise          : String? = "0",
  @SerializedName("sunset"            ) var sunset           : String? = "0",
  @SerializedName("moonrise"          ) var moonrise         : String? = "0",
  @SerializedName("moonset"           ) var moonset          : String? = "0",
  @SerializedName("moon_phase"        ) var moonPhase        : String? = "0",
  @SerializedName("moon_illumination" ) var moonIllumination : String? = "0",
  @SerializedName("is_moon_up"        ) var isMoonUp         : Int?    = 0,
  @SerializedName("is_sun_up"         ) var isSunUp          : Int?    = 0

)