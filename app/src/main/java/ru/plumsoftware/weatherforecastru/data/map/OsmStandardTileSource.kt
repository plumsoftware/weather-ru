package ru.plumsoftware.weatherforecastru.data.map

import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.util.MapTileIndex

object OsmStandardTileSource {
    private const val TILE_SIZE = 256

    val instance: OnlineTileSourceBase = object : OnlineTileSourceBase(
        "OSM-Standard",
        0,
        19,
        TILE_SIZE,
        ".png",
        arrayOf("https://tile.openstreetmap.org/"),
    ) {
        override fun getTileURLString(pMapTileIndex: Long): String =
            MapTileUrlBuilder.openStreetMapTile(
                zoom = MapTileIndex.getZoom(pMapTileIndex),
                x = MapTileIndex.getX(pMapTileIndex),
                y = MapTileIndex.getY(pMapTileIndex),
            )
    }
}
