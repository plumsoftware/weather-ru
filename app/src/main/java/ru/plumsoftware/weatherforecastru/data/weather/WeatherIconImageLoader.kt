package ru.plumsoftware.weatherforecastru.data.weather

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object WeatherIconImageLoader {
    fun initialize(context: Context) = Unit

    suspend fun loadBitmap(
        context: Context,
        iconCode: String,
        size: OwmIconSize,
    ): Bitmap? = withContext(Dispatchers.IO) {
        val drawable = ContextCompat.getDrawable(
            context,
            LocalWeatherIcons.drawableResForOwmCode(iconCode),
        ) ?: return@withContext null
        drawableToBitmap(drawable)
    }

    suspend fun loadMoonPhaseBitmap(
        context: Context,
        moonPhase: String,
    ): Bitmap? = withContext(Dispatchers.IO) {
        val drawable = ContextCompat.getDrawable(
            context,
            LocalWeatherIcons.drawableResForMoonPhase(moonPhase),
        ) ?: return@withContext null
        drawableToBitmap(drawable)
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable && drawable.bitmap != null) {
            return drawable.bitmap
        }
        val width = drawable.intrinsicWidth.coerceAtLeast(1)
        val height = drawable.intrinsicHeight.coerceAtLeast(1)
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).also { bitmap ->
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, width, height)
            drawable.draw(canvas)
        }
    }
}
