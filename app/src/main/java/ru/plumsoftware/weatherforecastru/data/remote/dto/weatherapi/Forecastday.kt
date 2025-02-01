package ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi

import com.google.gson.annotations.SerializedName


data class Forecastday (

  @SerializedName("date"       ) var date      : String?         = "",
  @SerializedName("date_epoch" ) var dateEpoch : Int?            = 0,
  @SerializedName("day"        ) var day       : Day?            = Day(),
  @SerializedName("astro"      ) var astro     : Astro?          = Astro(),
  @SerializedName("hour"       ) var hour      : ArrayList<Hour> = arrayListOf(Hour())

)