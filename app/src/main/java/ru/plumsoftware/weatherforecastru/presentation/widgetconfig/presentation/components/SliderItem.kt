package ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues

@Composable
fun SliderItem(
    title: String,
    startValue: Float,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (() -> Unit),
    colors: SliderColors = SliderDefaults.colors()
) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
        if (title.isNotEmpty())
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.outline)
            )
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = startValue,
            onValueChange = onValueChange,
            valueRange = range,
            steps = 0,
            colors = colors,
            onValueChangeFinished = onValueChangeFinished
        )
    }
}