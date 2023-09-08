package ru.plumsoftware.weatherforecastru.domain.remote.dto.owm

internal interface OwmResponse {
    var coord: Coord?
    var weather: ArrayList<Weather>
    var base: String?
    var main: Main?
    var visibility: Int?
    var wind: Wind?
    var rain: Rain?
    var clouds: Clouds?
    var dt: Int?
    var sys: Sys?
    var timezone: Int?
    var id: Int?
    var name: String?
    var cod: Int?
}