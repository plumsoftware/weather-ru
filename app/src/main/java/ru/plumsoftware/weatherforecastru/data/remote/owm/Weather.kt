package ru.plumsoftware.weatherforecastru.data.remote.owm

interface Weather {
    var id: Int?
    var main: String?
    var description: String?
    var icon: String?
}