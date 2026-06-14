package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.data.map.WeatherGridLabel

class WeatherGridLabelsOverlay(
    labels: List<WeatherGridLabel>,
) : Overlay() {
    private val labelItems = labels.toMutableList()
    private var labelTypeface: Typeface? = null

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
        textSize = 28f
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.argb(180, 20, 24, 32)
        style = Paint.Style.FILL
    }

    fun updateLabels(labels: List<WeatherGridLabel>) {
        labelItems.clear()
        labelItems.addAll(labels)
    }

    override fun draw(canvas: Canvas, mapView: MapView, shadow: Boolean) {
        if (shadow || labelItems.isEmpty()) return

        if (labelTypeface == null) {
            labelTypeface = ResourcesCompat.getFont(mapView.context, R.font.inter_18pt_semibold)
            textPaint.typeface = labelTypeface
        }

        val projection = mapView.projection
        val paddingX = 12f
        val paddingY = 8f
        val cornerRadius = 12f
        val textBounds = android.graphics.Rect()

        labelItems.forEach { label ->
            val screenPoint = projection.toPixels(GeoPoint(label.latitude, label.longitude), null)
            textPaint.getTextBounds(label.text, 0, label.text.length, textBounds)

            val boxWidth = textBounds.width() + paddingX * 2
            val boxHeight = textBounds.height() + paddingY * 2
            val left = screenPoint.x - boxWidth / 2f
            val top = screenPoint.y - boxHeight / 2f
            val rect = RectF(left, top, left + boxWidth, top + boxHeight)

            canvas.drawRoundRect(rect, cornerRadius, cornerRadius, backgroundPaint)
            canvas.drawText(
                label.text,
                screenPoint.x.toFloat(),
                screenPoint.y - textBounds.exactCenterY(),
                textPaint,
            )
        }
    }
}
