package ru.plumsoftware.weatherforecastru.domain.models.location

data class Location(
    val city: String,
    val country: String,
    val coords: LocationCoords? = null
) {
    constructor(city: String, country: String) : this(city = city, country = country, coords = null)

    constructor(city: String) : this(city = city, country = "", coords = null)
}