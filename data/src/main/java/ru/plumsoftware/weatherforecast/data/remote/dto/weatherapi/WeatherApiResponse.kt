package ru.plumsoftware.weatherforecast.data.remote.dto.weatherapi

import com.google.gson.annotations.SerializedName


data class WeatherApiResponse (

  @SerializedName("location" ) var location : Location? = Location(),
  @SerializedName("current"  ) var current  : Current?  = Current(),
  @SerializedName("alerts"   ) var alerts   : Alerts?   = Alerts(),
  @SerializedName("forecast" ) var forecast : Forecast? = Forecast()

)