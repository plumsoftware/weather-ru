package ru.plumsoftware.weatherforecastru.data.remote.owm

interface Sys {
    var type: Int?
    var id: Int?
    var country: String?
    var sunrise: Int?
    var sunset: Int?
}