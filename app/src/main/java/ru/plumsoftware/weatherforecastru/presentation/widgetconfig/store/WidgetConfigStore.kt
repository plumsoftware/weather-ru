package ru.plumsoftware.weatherforecastru.presentation.widgetconfig.store

import com.arkivanov.mvikotlin.core.store.Store
import ru.plumsoftware.weatherforecastru.data.models.widget.WidgetConfig

interface WidgetConfigStore :
    Store<WidgetConfigStore.Intent, WidgetConfigStore.State, WidgetConfigStore.Label> {

    sealed interface Intent {
        object BackButtonClicked : Intent

        data class RadiusChanged(val value: Int) : Intent
        data class RedChanged(val value: Int) : Intent
        data class GreenChanged(val value: Int) : Intent
        data class BlueChanged(val value: Int) : Intent

        object Save : Intent
    }

    data class State(
        val widgetConfig: WidgetConfig = WidgetConfig(),
        val radius: Int = widgetConfig.radius,
        val red: Int = widgetConfig.red,
        val green: Int = widgetConfig.green,
        val blue: Int = widgetConfig.blue
    )

    sealed interface Label {
        object BackButtonClicked : Label
    }
}