package ru.plumsoftware.weatherforecast.domain.models

data class Location(
    val city: String,
    val country: String,
    val coords: LocationCoords
)