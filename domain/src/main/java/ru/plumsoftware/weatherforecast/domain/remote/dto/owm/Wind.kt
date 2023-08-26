package ru.plumsoftware.weatherforecast.domain.remote.dto.owm


interface Wind {
    var speed: Double?
    var deg: Int?
    var gust: Double?
}