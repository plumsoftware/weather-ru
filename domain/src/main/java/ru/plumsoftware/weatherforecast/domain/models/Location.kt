package ru.plumsoftware.weatherforecast.domain.models

data class Location(
    val city: String,
    val country: String,
    val coords: LocationCoords? = null
) {
    constructor(city: String, country: String) : this(city = city, country = country, coords = null)
}