package ru.plumsoftware.weatherforecastru.data.map

import ru.plumsoftware.weatherforecast.BuildConfig

object MapTileUrlBuilder {
    private const val OSM_TILE_BASE = "https://tile.openstreetmap.org"
    private const val OWM_TILE_BASE = "https://tile.openweathermap.org/map"

    fun openStreetMapTile(zoom: Int, x: Int, y: Int): String =
        "$OSM_TILE_BASE/$zoom/$x/$y.png"

    fun owmTile(
        layerId: String,
        zoom: Int,
        x: Int,
        y: Int,
    ): String = "$OWM_TILE_BASE/$layerId/$zoom/$x/$y.png?appid=${BuildConfig.OWM_API_KEY}"
}
