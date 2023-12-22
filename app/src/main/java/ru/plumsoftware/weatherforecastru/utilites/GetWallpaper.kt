package ru.plumsoftware.weatherforecastru.utilites

import android.app.WallpaperManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap


fun getWallpaper(context: Context): ImageBitmap {
    val wallpaperManager = WallpaperManager.getInstance(context)
    val wallpaperDrawable = wallpaperManager.drawable

    val bitmap = (wallpaperDrawable as BitmapDrawable).bitmap

    return bitmap.asImageBitmap()
}