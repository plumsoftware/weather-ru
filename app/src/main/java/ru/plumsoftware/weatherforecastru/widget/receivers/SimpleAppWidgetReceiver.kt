package ru.plumsoftware.weatherforecastru.widget.receivers

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import ru.plumsoftware.weatherforecastru.widget.presentation.SimpleAppWidget

class SimpleAppWidgetReceiver :
    GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = SimpleAppWidget()
}