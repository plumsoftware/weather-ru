package ru.plumsoftware.weatherforecastru.data.map

import android.content.Context
import org.osmdroid.config.Configuration
import java.io.File

object OsmdroidInitializer {
    private const val CACHE_MAX_BYTES = 50L * 1024L * 1024L
    private const val CACHE_TTL_MS = 60L * 60L * 1000L

    fun initialize(context: Context) {
        val appContext = context.applicationContext
        val configuration = Configuration.getInstance()
        configuration.load(
            appContext,
            appContext.getSharedPreferences("osmdroid_prefs", Context.MODE_PRIVATE),
        )
        val versionName = runCatching {
            appContext.packageManager
                .getPackageInfo(appContext.packageName, 0)
                .versionName
        }.getOrNull().orEmpty().ifBlank { "1.0" }
        configuration.userAgentValue =
            "WeatherForecastRu/$versionName (${appContext.packageName})"
        configuration.osmdroidBasePath = File(appContext.cacheDir, "osmdroid")
        configuration.osmdroidTileCache = File(appContext.cacheDir, "osm_tiles")
        configuration.tileFileSystemCacheMaxBytes = CACHE_MAX_BYTES
        configuration.expirationOverrideDuration = CACHE_TTL_MS
        configuration.tileDownloadThreads = 4
    }
}
