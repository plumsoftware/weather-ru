package ru.plumsoftware.weatherforecastru.data.remote.owm

internal interface OwmResponse {
    var coord: Coord?
    var weather: ArrayList<Weather>
    var base: String?
    var main: ru.plumsoftware.weatherforecastru.data.remote.owm.Main?
    var visibility: Int?
    var wind: Wind?
    var rain: Rain?
    var clouds: Clouds?
    var dt: Int?
    var sys: ru.plumsoftware.weatherforecastru.data.remote.owm.Sys?
    var timezone: Int?
    var id: Int?
    var name: String?
    var cod: Int?
}