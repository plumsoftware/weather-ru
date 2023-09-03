package ru.plumsoftware.weatherforecast.data.remote.dto.weatherapi

import com.google.gson.annotations.SerializedName


data class Alerts (

  @SerializedName("alert" ) var alert : ArrayList<Alert> = arrayListOf()

)