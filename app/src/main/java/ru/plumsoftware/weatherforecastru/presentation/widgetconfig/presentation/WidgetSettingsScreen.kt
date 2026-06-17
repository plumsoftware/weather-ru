package ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.presentation.ui.NavigationBarSpacer

private val widgetColorPresets = listOf(
    WidgetColorPreset(R.string.widget_preset_white, 255, 255, 255),
    WidgetColorPreset(R.string.widget_preset_dark, 0x1A, 0x1E, 0x2E),
    WidgetColorPreset(R.string.widget_preset_indigo, 0x5C, 0x6B, 0xC0),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetSettingsScreen(
    state: WidgetSettingsUiState,
    onBack: () -> Unit,
    onRadiusChange: (Float) -> Unit,
    onColorPresetSelect: (WidgetColorPreset) -> Unit,
    onRedChange: (Float) -> Unit,
    onGreenChange: (Float) -> Unit,
    onBlueChange: (Float) -> Unit,
    onOpacityChange: (Float) -> Unit,
    radiusSupported: Boolean = true,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.widget_hint),
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item {
                WidgetPreviewSection(state = state)
            }

            item {
                if (radiusSupported) {
                    SettingsCard(
                        title = stringResource(R.string.widget_corner_radius),
                        value = stringResource(R.string.widget_radius_value, state.radius.toInt()),
                    ) {
                        LabeledSlider(
                            value = state.radius,
                            valueRange = 0f..32f,
                            accentColor = MaterialTheme.colorScheme.primary,
                            onValueChange = onRadiusChange,
                        )
                    }
                } else {
                    Text(
                        text = stringResource(R.string.radius_settings),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(horizontal = 4.dp),
                    )
                }
            }

            item {
                ColorSettingsCard(
                    state = state,
                    onPresetSelect = onColorPresetSelect,
                    onRedChange = onRedChange,
                    onGreenChange = onGreenChange,
                    onBlueChange = onBlueChange,
                )
            }

            item {
                SettingsCard(
                    title = stringResource(R.string.widget_opacity),
                    value = stringResource(R.string.widget_opacity_value, (state.opacity * 100).toInt()),
                ) {
                    LabeledSlider(
                        value = state.opacity,
                        valueRange = 0f..1f,
                        accentColor = MaterialTheme.colorScheme.primary,
                        onValueChange = onOpacityChange,
                    )
                }
            }

            item {
                NavigationBarSpacer()
            }
        }
    }
}

@Composable
private fun WidgetPreviewSection(state: WidgetSettingsUiState) {
    Column {
        Text(
            text = stringResource(R.string.widget_preview_label),
            style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.08.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(checkerboardBrush()),
            contentAlignment = Alignment.Center,
        ) {
            Surface(
                modifier = Modifier.padding(horizontal = 28.dp),
                shape = RoundedCornerShape(state.radius.dp),
                color = state.backgroundColor.copy(alpha = state.opacity),
                shadowElevation = 6.dp,
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.WbSunny,
                        contentDescription = null,
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(34.dp),
                    )
                    Column {
                        Text(
                            text = "15°",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = state.contentColor,
                        )
                        Text(
                            text = "10° / 18°",
                            style = MaterialTheme.typography.bodySmall,
                            color = state.contentColor.copy(alpha = 0.6f),
                        )
                    }
                }
            }
        }
    }
}

private fun checkerboardBrush(): Brush =
    Brush.linearGradient(
        colors = listOf(Color(0xFFE4E7F0), Color(0xFFD8DCE8)),
    )

@Composable
private fun SettingsCard(
    title: String,
    value: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 1.dp,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
private fun LabeledSlider(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    accentColor: Color,
    onValueChange: (Float) -> Unit,
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        colors = SliderDefaults.colors(
            thumbColor = accentColor,
            activeTrackColor = accentColor,
            inactiveTrackColor = accentColor.copy(alpha = 0.15f),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun ColorSettingsCard(
    state: WidgetSettingsUiState,
    onPresetSelect: (WidgetColorPreset) -> Unit,
    onRedChange: (Float) -> Unit,
    onGreenChange: (Float) -> Unit,
    onBlueChange: (Float) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 1.dp,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.widget_background_color),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                widgetColorPresets.forEach { preset ->
                    ColorPresetChip(
                        preset = preset,
                        isSelected = matchesPreset(state, preset),
                        onClick = { onPresetSelect(preset) },
                        modifier = Modifier.weight(1f),
                    )
                }
                CustomColorChip(
                    isSelected = widgetColorPresets.none { matchesPreset(state, it) },
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(state.backgroundColor)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(12.dp),
                        ),
                )
                Column {
                    Text(
                        text = colorToHex(state.backgroundColor),
                        style = MaterialTheme.typography.titleSmall.copy(fontFamily = FontFamily.Monospace),
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = stringResource(R.string.widget_current_color),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            RgbSlider(
                label = "R",
                value = state.red,
                gradientColors = listOf(Color.Black, Color.Red),
                thumbColor = Color(0xFFE53935),
                onValueChange = onRedChange,
            )
            Spacer(modifier = Modifier.height(10.dp))
            RgbSlider(
                label = "G",
                value = state.green,
                gradientColors = listOf(Color.Black, Color.Green),
                thumbColor = Color(0xFF27AE60),
                onValueChange = onGreenChange,
            )
            Spacer(modifier = Modifier.height(10.dp))
            RgbSlider(
                label = "B",
                value = state.blue,
                gradientColors = listOf(Color.Black, Color.Blue),
                thumbColor = Color(0xFF3D5AFE),
                onValueChange = onBlueChange,
            )
        }
    }
}

private fun matchesPreset(state: WidgetSettingsUiState, preset: WidgetColorPreset): Boolean =
    state.red.toInt() == preset.red &&
        state.green.toInt() == preset.green &&
        state.blue.toInt() == preset.blue

@Composable
private fun ColorPresetChip(
    preset: WidgetColorPreset,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                },
                shape = RoundedCornerShape(12.dp),
            )
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(preset.color)
                .border(
                    width = 0.5.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(7.dp),
                ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(preset.nameResId),
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        )
    }
}

@Composable
private fun CustomColorChip(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                },
                shape = RoundedCornerShape(12.dp),
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFF667EEA), Color(0xFF764BA2)),
                    ),
                ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.widget_preset_custom),
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        )
    }
}

@Composable
private fun RgbSlider(
    label: String,
    value: Float,
    gradientColors: List<Color>,
    thumbColor: Color,
    onValueChange: (Float) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = value.toInt().toString(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(modifier = Modifier.fillMaxWidth().height(24.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Brush.horizontalGradient(gradientColors)),
            )
            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = 0f..255f,
                colors = SliderDefaults.colors(
                    thumbColor = thumbColor,
                    activeTrackColor = Color.Transparent,
                    inactiveTrackColor = Color.Transparent,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

private fun colorToHex(color: Color): String {
    val argb = color.toArgb()
    return String.format("#%06X", 0xFFFFFF and argb)
}
