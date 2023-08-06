package ru.plumsoftware.weatherforecast.data.models

data class Location(
    val city: String,
    val country: String,
    val coords: LocationCoords
)