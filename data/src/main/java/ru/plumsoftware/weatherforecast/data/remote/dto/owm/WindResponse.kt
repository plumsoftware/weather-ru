package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Wind

@Serializable
data class WindResponse(
    override var speed: Double? = null,
    override var deg: Int? = null,
    override var gust: Double? = null
) : Wind