package ru.plumsoftware.weatherforecastru.widgets.receivers

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import ru.plumsoftware.weatherforecastru.widgets.presentation.SimpleBlueAppWidget

class SimpleBlueAppWidgetReceiver :
    GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = SimpleBlueAppWidget
}