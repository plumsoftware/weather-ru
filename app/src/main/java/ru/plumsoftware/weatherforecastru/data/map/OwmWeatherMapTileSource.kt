package ru.plumsoftware.weatherforecastru.data.map

import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.util.MapTileIndex

object OwmWeatherMapTileSource {
    private const val TILE_SIZE = 256

    fun create(layer: OwmTileLayer): OnlineTileSourceBase = object : OnlineTileSourceBase(
        "OWM-${layer.layerId}",
        1,
        19,
        TILE_SIZE,
        ".png",
        arrayOf("https://tile.openweathermap.org/map/${layer.layerId}/"),
    ) {
        override fun getTileURLString(pMapTileIndex: Long): String =
            MapTileUrlBuilder.owmTile(
                layerId = layer.layerId,
                zoom = MapTileIndex.getZoom(pMapTileIndex),
                x = MapTileIndex.getX(pMapTileIndex),
                y = MapTileIndex.getY(pMapTileIndex),
            )
    }
}
