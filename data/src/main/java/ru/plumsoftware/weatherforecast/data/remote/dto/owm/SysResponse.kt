package ru.plumsoftware.weatherforecast.data.remote.dto.owm

import kotlinx.serialization.Serializable
import ru.plumsoftware.weatherforecast.domain.remote.dto.owm.Sys

@Serializable
data class SysResponse(
    override var type: Int? = null,
    override var id: Int? = null,
    override var country: String? = null,
    override var sunrise: Int? = null,
    override var sunset: Int? = null
) : Sys