package ru.plumsoftware.weatherforecastru.domain.remote.dto.owm

interface Sys {
    var type: Int?
    var id: Int?
    var country: String?
    var sunrise: Int?
    var sunset: Int?
}