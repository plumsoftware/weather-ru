package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import android.graphics.Canvas
import android.view.ViewGroup
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.flow.distinctUntilChanged
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.CopyrightOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.TilesOverlay
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.data.map.MapGridCalculator
import ru.plumsoftware.weatherforecastru.data.map.MapViewportCalculator
import ru.plumsoftware.weatherforecastru.data.map.OsmStandardTileSource
import ru.plumsoftware.weatherforecastru.data.map.OwmOverlaySpec
import ru.plumsoftware.weatherforecastru.data.map.OwmWeatherMapTileSource
import ru.plumsoftware.weatherforecastru.data.map.WeatherGridLabel

private const val MIN_ZOOM = 3.0
private const val MAX_ZOOM = 19.0
private const val LAYER_CROSSFADE_MS = 200
private const val LAYER_CROSSFADE_HALF_MS = LAYER_CROSSFADE_MS / 2

private class OverlayAlphaHolder(var multiplier: Float = 1f)

private class AlphaTilesOverlay(
    provider: MapTileProviderBasic,
    context: android.content.Context,
    private val baseAlpha: Int,
    private val alphaHolder: OverlayAlphaHolder,
) : TilesOverlay(provider, context) {
    override fun draw(canvas: Canvas, mapView: MapView, shadow: Boolean) {
        if (shadow) return
        val effectiveAlpha = (baseAlpha * alphaHolder.multiplier).toInt().coerceIn(0, 255)
        if (effectiveAlpha == 0) return
        val saveCount = canvas.saveLayerAlpha(
            0f,
            0f,
            canvas.width.toFloat(),
            canvas.height.toFloat(),
            effectiveAlpha,
        )
        super.draw(canvas, mapView, false)
        canvas.restoreToCount(saveCount)
    }
}

@Composable
fun PrecipitationOsmdroidMap(
    latitude: Double,
    longitude: Double,
    overlaySpecs: List<OwmOverlaySpec>,
    gridLabels: List<WeatherGridLabel>,
    modifier: Modifier = Modifier,
    interactive: Boolean = false,
    mapInstanceKey: String = if (interactive) "fullscreen" else "preview",
) {
    val context = LocalContext.current
    val appContext = context.applicationContext
    val lifecycleOwner = LocalLifecycleOwner.current
    val overlayHolder = remember(mapInstanceKey) { mutableListOf<TilesOverlay>() }
    val copyrightOverlay = remember(mapInstanceKey) { CopyrightOverlay(appContext) }
    val gridLabelsOverlay = remember(mapInstanceKey) { WeatherGridLabelsOverlay(emptyList()) }
    val alphaHolder = remember(mapInstanceKey) { OverlayAlphaHolder() }
    val layerFade = remember(mapInstanceKey) { Animatable(1f) }
    val overlayKey = remember(mapInstanceKey) {
        overlaySpecs.joinToString("-") { "${it.layer.layerId}:${it.alpha}" }
    }

    val mapView = remember(mapInstanceKey) {
        MapView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            setMultiTouchControls(interactive)
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
            setBuiltInZoomControls(false)
            isHorizontalMapRepetitionEnabled = true
            isVerticalMapRepetitionEnabled = false
            isClickable = interactive
            isFocusable = interactive
            isFocusableInTouchMode = interactive
            isTilesScaledToDpi = true
            setUseDataConnection(true)
            setTileSource(OsmStandardTileSource.instance)
            minZoomLevel = MIN_ZOOM
            maxZoomLevel = MAX_ZOOM
            if (!overlays.contains(copyrightOverlay)) {
                overlays.add(copyrightOverlay)
            }
        }
    }

    val locationMarker = remember(mapView) {
        Marker(mapView).apply {
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            icon = ContextCompat.getDrawable(appContext, R.drawable.map_location_pin)
            isDraggable = false
            setOnMarkerClickListener { _, _ -> true }
        }
    }

    fun applyOwmOverlays(map: MapView, specs: List<OwmOverlaySpec>) {
        overlayHolder.forEach { map.overlays.remove(it) }
        overlayHolder.clear()
        map.overlays.remove(gridLabelsOverlay)
        map.overlays.remove(locationMarker)

        specs.forEach { spec ->
            val overlay = AlphaTilesOverlay(
                MapTileProviderBasic(appContext, OwmWeatherMapTileSource.create(spec.layer)),
                appContext,
                (255 * spec.alpha).toInt(),
                alphaHolder,
            ).apply {
                setLoadingBackgroundColor(android.graphics.Color.TRANSPARENT)
            }
            map.overlays.add(overlay)
            overlayHolder.add(overlay)
        }

        map.overlays.add(gridLabelsOverlay)
        map.overlays.add(locationMarker)
    }

    fun zoomToCurrentRadius(map: MapView, center: GeoPoint) {
        val boundingBox = MapViewportCalculator.boundingBoxForRadius(
            center = center,
            radiusMeters = MapGridCalculator.MAP_RADIUS_METERS,
        )
        map.post {
            map.zoomToBoundingBox(boundingBox, false)
        }
    }

    fun centerMap(map: MapView, forceZoom: Boolean = false) {
        if (latitude == 0.0 && longitude == 0.0) return
        val center = GeoPoint(latitude, longitude)
        locationMarker.position = center

        if (!interactive || forceZoom) {
            map.controller.setCenter(center)
            zoomToCurrentRadius(map, center)
        }

        map.invalidate()
    }

    DisposableEffect(lifecycleOwner, mapView) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        mapView.onResume()
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView.onPause()
        }
    }

    LaunchedEffect(mapView, layerFade) {
        snapshotFlow { layerFade.value }
            .distinctUntilChanged()
            .collect { value ->
                alphaHolder.multiplier = value
                mapView.postInvalidate()
            }
    }

    LaunchedEffect(gridLabels) {
        gridLabelsOverlay.updateLabels(gridLabels)
        mapView.postInvalidate()
    }

    val hasInitializedOverlays = remember(mapInstanceKey) { mutableStateOf(false) }

    LaunchedEffect(latitude, longitude, overlayKey) {
        if (!hasInitializedOverlays.value) {
            hasInitializedOverlays.value = true
            layerFade.snapTo(1f)
            alphaHolder.multiplier = 1f
            mapView.post {
                applyOwmOverlays(mapView, overlaySpecs)
                centerMap(mapView, forceZoom = true)
                mapView.invalidate()
            }
            return@LaunchedEffect
        }
        layerFade.animateTo(0f, tween(LAYER_CROSSFADE_HALF_MS))
        mapView.post {
            applyOwmOverlays(mapView, overlaySpecs)
        }
        layerFade.animateTo(1f, tween(LAYER_CROSSFADE_HALF_MS))
    }

    LaunchedEffect(latitude, longitude) {
        if (!interactive) {
            mapView.post { centerMap(mapView, forceZoom = true) }
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            mapView.apply {
                alphaHolder.multiplier = layerFade.value
                gridLabelsOverlay.updateLabels(gridLabels)
                applyOwmOverlays(this, overlaySpecs)
                post { centerMap(this, forceZoom = true) }
            }
        },
        update = { map ->
            map.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            map.setMultiTouchControls(interactive)
            map.isClickable = interactive
            map.isFocusable = interactive
            map.isFocusableInTouchMode = interactive
            map.minZoomLevel = MIN_ZOOM
            map.maxZoomLevel = MAX_ZOOM
            gridLabelsOverlay.updateLabels(gridLabels)
            if (!interactive) {
                map.setOnTouchListener { _, _ -> false }
                centerMap(map, forceZoom = true)
            } else {
                map.setOnTouchListener(null)
            }
            map.post { map.invalidate() }
        },
        onRelease = { map ->
            map.onPause()
        },
    )
}
