package ru.plumsoftware.weatherforecastru.widgets.receivers

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import ru.plumsoftware.weatherforecastru.widgets.presentation.SimpleDarkAppWidget

class SimpleDarkAppWidgetReceiver :
    GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = SimpleDarkAppWidget()
}