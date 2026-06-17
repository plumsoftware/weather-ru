package ru.plumsoftware.weatherforecastru.widget.utilites

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import ru.plumsoftware.weatherforecastru.widget.presentation.SimpleAppWidget

object WidgetConfigUpdateHelper {

    suspend fun requestWidgetUpdate(context: Context) {
        val manager = GlanceAppWidgetManager(context)
        val widget = SimpleAppWidget()
        manager.getGlanceIds(widget::class.java).forEach { glanceId ->
            widget.update(context, glanceId)
        }
    }
}
