package ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.store.WidgetConfigStore
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.viewmodel.WidgetConfigViewModel

@Composable
fun WidgetConfig(widgetConfigViewModel: WidgetConfigViewModel) {
    val state by widgetConfigViewModel.state.collectAsState()

    LaunchedEffect(key1 = widgetConfigViewModel) {
        widgetConfigViewModel.label.collect { label ->
            when (label) {
                WidgetConfigStore.Label.BackButtonClicked -> widgetConfigViewModel.onOutput(
                    WidgetConfigViewModel.Output.BackStackClicked,
                )
            }
        }
    }

    WidgetConfig(
        event = widgetConfigViewModel::onEvent,
        state = state,
    )
}

@Composable
fun WidgetConfig(
    event: (WidgetConfigStore.Intent) -> Unit,
    state: WidgetConfigStore.State,
) {
    val currentVersion by produceState(initialValue = android.os.Build.VERSION.SDK_INT) {
        value = android.os.Build.VERSION.SDK_INT
    }

    WidgetSettingsScreen(
        state = state.toUiState(),
        onBack = { event(WidgetConfigStore.Intent.BackButtonClicked) },
        onRadiusChange = { value ->
            event(WidgetConfigStore.Intent.RadiusChanged(value = value.toInt()))
        },
        onColorPresetSelect = { preset ->
            event(
                WidgetConfigStore.Intent.ColorPresetSelected(
                    red = preset.red,
                    green = preset.green,
                    blue = preset.blue,
                ),
            )
        },
        onRedChange = { value ->
            event(WidgetConfigStore.Intent.RedChanged(value = value.toInt()))
        },
        onGreenChange = { value ->
            event(WidgetConfigStore.Intent.GreenChanged(value = value.toInt()))
        },
        onBlueChange = { value ->
            event(WidgetConfigStore.Intent.BlueChanged(value = value.toInt()))
        },
        onOpacityChange = { value ->
            event(WidgetConfigStore.Intent.OpacityChanged(value = value))
        },
        radiusSupported = currentVersion >= 31,
    )
}
