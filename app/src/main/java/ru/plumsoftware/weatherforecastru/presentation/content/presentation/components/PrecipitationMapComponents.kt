package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.OpenInFull
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.data.map.WeatherGridLabel
import ru.plumsoftware.weatherforecastru.data.map.WeatherMapLayer
import ru.plumsoftware.weatherforecastru.data.map.previewWeatherMapOverlaySpecs
import ru.plumsoftware.weatherforecastru.data.map.toFullscreenOverlaySpecs
import ru.plumsoftware.weatherforecastru.presentation.ui.Dimens
import ru.plumsoftware.weatherforecastru.presentation.ui.bold
import ru.plumsoftware.weatherforecastru.presentation.ui.medium
import ru.plumsoftware.weatherforecastru.presentation.ui.regular
import ru.plumsoftware.weatherforecastru.presentation.ui.components.SectionHeader
import ru.plumsoftware.weatherforecastru.presentation.ui.components.WeatherCard
import ru.plumsoftware.weatherforecastru.presentation.ui.navigationBarBottomInset
import ru.plumsoftware.weatherforecastru.presentation.ui.statusBarTopPadding

private const val MAP_ANIMATION_MS = 350
private const val MAP_LOADING_PROGRESS_MS = 1400
private const val TITLE_FADE_MS = 150
private const val LEGEND_BAR_WIDTH = 160
private const val LEGEND_BAR_HEIGHT = 8
private const val LEGEND_BAR_RADIUS = 4

private val TemperatureGradientColors = listOf(
    Color(0xFF91B9F0),
    Color(0xFF5BA6F5),
    Color(0xFFFFFFFF),
    Color(0xFFFFD700),
    Color(0xFFFF6E00),
    Color(0xFFC0392B),
)

private val CloudGradientColors = listOf(
    Color(0x00FFFFFF),
    Color(0xFFB0C4DE),
    Color(0xFF4A6FA5),
)

private val WindGradientColors = listOf(
    Color(0xFFA8F0A8),
    Color(0xFFF5E642),
    Color(0xFFFF4500),
)

@Composable
fun PrecipitationMapCard(
    latitude: Double,
    longitude: Double,
    gridLabels: List<WeatherGridLabel>,
    onExpand: () -> Unit,
    modifier: Modifier = Modifier,
) {
    WeatherCard(
        modifier = modifier
            .padding(horizontal = Dimens.screenPaddingH)
            .fillMaxWidth(),
    ) {
        SectionHeader(
            title = stringResource(R.string.weather_map_title),
            icon = null,
            titleFontWeight = FontWeight.Medium,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.mapCollapsedHeight)
                .clip(RoundedCornerShape(Dimens.cardRadius))
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            if (latitude != 0.0 || longitude != 0.0) {
                PrecipitationOsmdroidMap(
                    latitude = latitude,
                    longitude = longitude,
                    overlaySpecs = previewWeatherMapOverlaySpecs(),
                    gridLabels = gridLabels,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(Dimens.cardRadius)),
                    interactive = false,
                    mapInstanceKey = "preview-$latitude-$longitude",
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onExpand,
                    ),
            )
            MapAttributionBadge(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(4.dp),
                showOwmAttribution = false,
            )
            FilledIconButton(
                onClick = onExpand,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(Dimens.itemGap),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                    contentColor = MaterialTheme.colorScheme.primary,
                ),
            ) {
                Icon(
                    Icons.Outlined.OpenInFull,
                    contentDescription = stringResource(R.string.expand_map),
                )
            }
            WeatherMapLegend(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(Dimens.cardPadding),
                mapLayer = WeatherMapLayer.Temperature,
                compact = true,
            )
        }
    }
}

@Composable
fun PrecipitationMapFullScreen(
    visible: Boolean,
    latitude: Double,
    longitude: Double,
    mapLayer: WeatherMapLayer,
    gridLabels: List<WeatherGridLabel>,
    city: String,
    country: String,
    onMapLayerChange: (WeatherMapLayer) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (!visible) return

    AnimatedVisibility(
        visible = true,
        modifier = modifier,
        enter = fadeIn(tween(MAP_ANIMATION_MS, easing = FastOutSlowInEasing)) +
            scaleIn(
                initialScale = 0.92f,
                animationSpec = tween(MAP_ANIMATION_MS, easing = FastOutSlowInEasing),
            ),
        exit = fadeOut(tween(MAP_ANIMATION_MS, easing = FastOutSlowInEasing)) +
            scaleOut(
                targetScale = 0.92f,
                animationSpec = tween(MAP_ANIMATION_MS, easing = FastOutSlowInEasing),
            ),
    ) {
        BackHandler(onBack = onBack)
        var isMapLoading by remember { mutableStateOf(true) }

        LaunchedEffect(mapLayer) {
            isMapLoading = true
            delay(MAP_LOADING_PROGRESS_MS.toLong())
            isMapLoading = false
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            if (latitude != 0.0 || longitude != 0.0) {
                key("fullscreen-map-$latitude-$longitude-$mapLayer") {
                    PrecipitationOsmdroidMap(
                        latitude = latitude,
                        longitude = longitude,
                        overlaySpecs = mapLayer.toFullscreenOverlaySpecs(),
                        gridLabels = gridLabels,
                        modifier = Modifier.fillMaxSize(),
                        interactive = true,
                        mapInstanceKey = "fullscreen-$latitude-$longitude",
                    )
                }
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        end = Dimens.screenPaddingH,
                        bottom = 120.dp + navigationBarBottomInset(),
                    ),
            ) {
                MapAttributionBadge(showOwmAttribution = true)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarTopPadding(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = Dimens.itemGap),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                    AnimatedContent(
                        targetState = mapLayer,
                        modifier = Modifier.weight(1f),
                        transitionSpec = {
                            fadeIn(tween(TITLE_FADE_MS)) togetherWith fadeOut(tween(TITLE_FADE_MS))
                        },
                        label = "mapTitleFade",
                    ) { activeLayer ->
                        Text(
                            text = stringResource(
                                when (activeLayer) {
                                    WeatherMapLayer.Temperature -> R.string.map_full_title_temperature
                                    WeatherMapLayer.CloudsWind -> R.string.map_full_title_clouds_wind
                                },
                                city,
                            ),
                            style = MaterialTheme.typography.titleMedium.bold(),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                        )
                    }
                    Spacer(Modifier.width(48.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.screenPaddingH),
                    horizontalArrangement = Arrangement.End,
                ) {
                    WeatherMapLegend(
                        mapLayer = mapLayer,
                        compact = false,
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        start = Dimens.screenPaddingH,
                        bottom = Dimens.screenPaddingV + navigationBarBottomInset(),
                    ),
                shape = RoundedCornerShape(Dimens.cardRadius),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                border = androidx.compose.foundation.BorderStroke(
                    Dimens.cardBorder,
                    MaterialTheme.colorScheme.outlineVariant,
                ),
            ) {
                Text(
                    text = if (country.isNotBlank()) "$city, $country" else city,
                    style = MaterialTheme.typography.bodySmall.regular(),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = Dimens.cardPadding, vertical = Dimens.itemGap),
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        bottom = Dimens.screenPaddingV + navigationBarBottomInset(),
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimens.itemGap),
            ) {
                AnimatedVisibility(
                    visible = isMapLoading,
                    enter = fadeIn(tween(MAP_ANIMATION_MS, easing = FastOutSlowInEasing)),
                    exit = fadeOut(tween(MAP_ANIMATION_MS, easing = FastOutSlowInEasing)),
                ) {
                    MapLoadingBanner()
                }
                WeatherMapLayerSwitcher(
                    selectedLayer = mapLayer,
                    onLayerChange = onMapLayerChange,
                )
            }
        }
    }
}

@Composable
private fun MapLoadingBanner() {
    val infiniteTransition = rememberInfiniteTransition(label = "mapLoadingProgress")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = MAP_LOADING_PROGRESS_MS, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "mapLoadingProgressValue",
    )

    Surface(
        modifier = Modifier
            .padding(horizontal = Dimens.screenPaddingH)
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.cardRadius),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.94f),
        border = androidx.compose.foundation.BorderStroke(
            Dimens.cardBorder,
            MaterialTheme.colorScheme.outlineVariant,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.cardPadding, vertical = Dimens.itemGap),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = stringResource(R.string.loading_map),
                style = MaterialTheme.typography.labelSmall.regular(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.tempRangeBarHeight)
                    .clip(RoundedCornerShape(Dimens.tempRangeBarHeight / 2)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}

@Composable
private fun WeatherMapLegend(
    mapLayer: WeatherMapLayer,
    compact: Boolean,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(Dimens.cardRadius),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.88f),
    ) {
        when (mapLayer) {
            WeatherMapLayer.Temperature -> {
                GradientLegendRow(
                    startLabel = stringResource(R.string.map_legend_temp_min),
                    endLabel = stringResource(R.string.map_legend_temp_max),
                    colors = TemperatureGradientColors,
                    compact = compact,
                )
            }
            WeatherMapLayer.CloudsWind -> {
                Column(
                    modifier = Modifier.padding(
                        horizontal = if (compact) Dimens.itemGap else Dimens.cardPadding,
                        vertical = if (compact) 4.dp else Dimens.itemGap,
                    ),
                    verticalArrangement = Arrangement.spacedBy(if (compact) 4.dp else 6.dp),
                ) {
                    GradientLegendRow(
                        startLabel = stringResource(R.string.map_legend_cloud_min),
                        endLabel = stringResource(R.string.map_legend_cloud_max),
                        colors = CloudGradientColors,
                        compact = compact,
                    )
                    GradientLegendRow(
                        startLabel = stringResource(R.string.map_legend_wind_min),
                        endLabel = stringResource(R.string.map_legend_wind_max),
                        colors = WindGradientColors,
                        compact = compact,
                    )
                }
            }
        }
    }
}

@Composable
private fun GradientLegendRow(
    startLabel: String,
    endLabel: String,
    colors: List<Color>,
    compact: Boolean,
) {
    val labelStyle = MaterialTheme.typography.labelSmall.copy(
        fontSize = 10.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    ).regular()
    Row(
        modifier = Modifier.padding(
            horizontal = if (compact) Dimens.itemGap else 0.dp,
            vertical = if (compact) 4.dp else 0.dp,
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.itemGap),
    ) {
        Text(
            text = startLabel,
            style = labelStyle,
        )
        Box(
            modifier = Modifier
                .width(LEGEND_BAR_WIDTH.dp)
                .height(LEGEND_BAR_HEIGHT.dp)
                .clip(RoundedCornerShape(LEGEND_BAR_RADIUS.dp))
                .background(Brush.horizontalGradient(colors)),
        )
        Text(
            text = endLabel,
            style = labelStyle,
        )
    }
}

@Composable
private fun WeatherMapLayerSwitcher(
    selectedLayer: WeatherMapLayer,
    onLayerChange: (WeatherMapLayer) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .padding(horizontal = Dimens.screenPaddingH)
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.cardRadius),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.94f),
        border = androidx.compose.foundation.BorderStroke(
            Dimens.cardBorder,
            MaterialTheme.colorScheme.outlineVariant,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.itemGap),
            horizontalArrangement = Arrangement.spacedBy(Dimens.itemGap),
        ) {
            WeatherMapLayerSegment(
                label = stringResource(R.string.map_layer_temperature),
                selected = selectedLayer == WeatherMapLayer.Temperature,
                onClick = { onLayerChange(WeatherMapLayer.Temperature) },
                modifier = Modifier.weight(1f),
            )
            WeatherMapLayerSegment(
                label = stringResource(R.string.map_layer_clouds_wind),
                selected = selectedLayer == WeatherMapLayer.CloudsWind,
                onClick = { onLayerChange(WeatherMapLayer.CloudsWind) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun WeatherMapLayerSegment(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(Dimens.iconBoxRadius),
        color = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            Color.Transparent
        },
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.regular(),
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
            color = if (selected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.itemGap, vertical = 8.dp),
        )
    }
}

@Composable
private fun MapAttributionBadge(
    modifier: Modifier = Modifier,
    showOwmAttribution: Boolean,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(Dimens.iconBoxRadius),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.88f),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
        ) {
            Text(
                text = stringResource(R.string.map_attribution_osm),
                style = MaterialTheme.typography.labelSmall.regular(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            if (showOwmAttribution) {
                Text(
                    text = stringResource(R.string.map_attribution_owm),
                    style = MaterialTheme.typography.labelSmall.regular(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
