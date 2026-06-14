package ru.plumsoftware.weatherforecastru.data.map

enum class WeatherMapLayer {
    Temperature,
    CloudsWind,
}

enum class OwmTileLayer(val layerId: String) {
    TEMPERATURE("temp_new"),
    CLOUDS("clouds_new"),
    WIND("wind_new"),
}

data class OwmOverlaySpec(
    val layer: OwmTileLayer,
    val alpha: Float,
)

fun WeatherMapLayer.toFullscreenOverlaySpecs(): List<OwmOverlaySpec> = when (this) {
    WeatherMapLayer.Temperature -> listOf(OwmOverlaySpec(OwmTileLayer.TEMPERATURE, 0.6f))
    WeatherMapLayer.CloudsWind -> listOf(
        OwmOverlaySpec(OwmTileLayer.CLOUDS, 0.6f),
        OwmOverlaySpec(OwmTileLayer.WIND, 0.6f),
    )
}

fun previewWeatherMapOverlaySpecs(): List<OwmOverlaySpec> =
    listOf(OwmOverlaySpec(OwmTileLayer.TEMPERATURE, 0.55f))
