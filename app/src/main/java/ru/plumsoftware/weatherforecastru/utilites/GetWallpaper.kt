package ru.plumsoftware.weatherforecastru.utilites

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val MAX_WALLPAPER_EDGE_PX = 2048

suspend fun loadWallpaperImage(context: Context): ImageBitmap? = withContext(Dispatchers.IO) {
    loadWallpaperBitmap(context)?.asImageBitmap()
}

private fun loadWallpaperBitmap(context: Context): Bitmap? {
    val wallpaperManager = WallpaperManager.getInstance(context)

    runCatching {
        drawableToBitmap(wallpaperManager.peekDrawable())
    }.getOrNull()
        ?.takeIf { it.width > 1 && it.height > 1 }
        ?.let { return downscaleIfNeeded(it) }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        listOf(WallpaperManager.FLAG_SYSTEM, WallpaperManager.FLAG_LOCK).forEach { flag ->
            runCatching {
                wallpaperManager.getWallpaperFile(flag)?.use { parcelFileDescriptor ->
                    BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.fileDescriptor)
                }
            }.getOrNull()
                ?.takeIf { it.width > 1 && it.height > 1 }
                ?.let { return downscaleIfNeeded(it) }
        }
    }

    return runCatching {
        drawableToBitmap(wallpaperManager.drawable)
    }.getOrNull()
        ?.takeIf { it.width > 1 && it.height > 1 }
        ?.let { downscaleIfNeeded(it) }
}

private fun downscaleIfNeeded(bitmap: Bitmap): Bitmap {
    val maxEdge = maxOf(bitmap.width, bitmap.height)
    if (maxEdge <= MAX_WALLPAPER_EDGE_PX) return bitmap

    val scale = MAX_WALLPAPER_EDGE_PX.toFloat() / maxEdge.toFloat()
    val targetWidth = (bitmap.width * scale).toInt().coerceAtLeast(1)
    val targetHeight = (bitmap.height * scale).toInt().coerceAtLeast(1)
    return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
}

private fun drawableToBitmap(drawable: Drawable?): Bitmap? {
    if (drawable == null) return null

    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }

    val width = drawable.intrinsicWidth.coerceAtLeast(1)
    val height = drawable.intrinsicHeight.coerceAtLeast(1)
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, width, height)
    drawable.draw(canvas)
    return bitmap
}
