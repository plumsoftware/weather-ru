package ru.plumsoftware.weatherforecastru.widgets.presentation

import android.content.Context

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import ru.plumsoftware.weatherforecastru.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.uicomponents.R as UI
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionSize
import ru.plumsoftware.weatherforecastru.widgets.di.WidgetDI
import ru.plumsoftware.weatherforecastru.widgets.keys.Keys
import ru.plumsoftware.weatherforecastru.widgets.utilites.doHttpResponse
import ru.plumsoftware.weatherforecastru.widgets.material.PlumsoftwareWidgetTheme
import ru.plumsoftware.weatherforecastru.widgets.utilites.badIconToGoodIcon


class SimpleDarkAppWidget : GlanceAppWidget(), KoinComponent {

    private val currentDegreeKey = doublePreferencesKey(name = Keys.Simple.CURRENT_DEGREE)
    private val currentMinDegreeKey = doublePreferencesKey(name = Keys.Simple.CURRENT_MIN_DEGREE)
    private val currentMaxDegreeKey = doublePreferencesKey(name = Keys.Simple.CURRENT_MAX_DEGREE)
    private val currentIconIdKey = intPreferencesKey(name = Keys.Simple.CURRENT_ICON_ID)

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            // create your AppWidget here
            GlanceTheme(colors = PlumsoftwareWidgetTheme.PlumsoftwareWidgetColorScheme.colors) {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {

//        region::Variables
        val currentDegree = currentState(key = currentDegreeKey) ?: 0.001
        val currentMinDegree = currentState(key = currentMinDegreeKey) ?: 0.001
        val currentMaxDegree = currentState(key = currentMaxDegreeKey) ?: 0.001
        val currentIconId = currentState(key = currentIconIdKey) ?: UI.drawable.clear_day

        val coroutine = rememberCoroutineScope()
        val context = LocalContext.current

        val manager = GlanceAppWidgetManager(context)
        val widget = SimpleDarkAppWidget()
//        endregion

        coroutine.launch {
            val httpResponse: Pair<Pair<HttpStatusCode, HttpStatusCode>, Pair<OwmResponse, WeatherApiResponse>> =
                doHttpResponse(WidgetDI.httpClientStorage)
            val owmResponse: OwmResponse = httpResponse.second.first

            update(manager = manager, widget = widget, context = context, owmResponse = owmResponse)
        }

        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(imageProvider = ImageProvider(resId = UI.drawable.widget_default_dark_shape))
                .clickable(onClick = actionRunCallback<RefreshActionDark>()),
            contentAlignment = Alignment.Center
        ) {
            if (currentDegree == 0.001) {
                CircularProgressIndicator()
            } else {
                Row(
                    modifier = GlanceModifier.padding(all = ExtensionPaddingValues._8dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        provider = ImageProvider(resId = badIconToGoodIcon(icon = currentIconId)),
                        contentDescription = null,
                        modifier = GlanceModifier
                            .size(size = ExtensionSize.IconSize._44dp),
                        colorFilter = ColorFilter.tint(colorProvider = ColorProvider(color = Color(0xFFE4E1E6))),
                    )
                    Spacer(modifier = GlanceModifier.width(width = ExtensionPaddingValues._10dp))
                    Column(
                        modifier = GlanceModifier,
                        horizontalAlignment = Alignment.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${currentDegree.toInt()}°",
                            style = PlumsoftwareWidgetTheme.PlumsoftwareWidgetStyle.bold.copy(color = ColorProvider(color = Color(0xFFE4E1E6)))
                        )
                        Row(
                            modifier = GlanceModifier,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${currentMinDegree.toInt()}°",
                                style = PlumsoftwareWidgetTheme.PlumsoftwareWidgetStyle.regular.copy(
                                    color = GlanceTheme.colors.outline
                                )
                            )
                            Spacer(modifier = GlanceModifier.width(width = ExtensionPaddingValues._4dp))
                            Text(
                                text = "${currentMaxDegree.toInt()}°",
                                style = PlumsoftwareWidgetTheme.PlumsoftwareWidgetStyle.regular.copy(
                                    color = ColorProvider(color = Color(0xFFE4E1E6))
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    suspend fun update(
        manager: GlanceAppWidgetManager,
        widget: GlanceAppWidget,
        context: Context,
        owmResponse: OwmResponse
    ) {
        val glanceIds = manager.getGlanceIds(widget::class.java)
        glanceIds.forEach { glanceId ->
            updateAppWidgetState(context, glanceId) { prefs ->
                val current = prefs[SimpleDarkAppWidget().currentDegreeKey]
                val min = prefs[SimpleDarkAppWidget().currentMinDegreeKey]
                val max = prefs[SimpleDarkAppWidget().currentMaxDegreeKey]
                val iconId = prefs[SimpleDarkAppWidget().currentIconIdKey]

                prefs[SimpleDarkAppWidget().currentDegreeKey] = 0.001

                if (current != null) {
                    prefs[SimpleDarkAppWidget().currentDegreeKey] = owmResponse.main!!.temp!!
                } else {
                    prefs[SimpleDarkAppWidget().currentDegreeKey] = 0.001
                }

                if (min != null) {
                    prefs[SimpleDarkAppWidget().currentMinDegreeKey] =
                        owmResponse.main!!.tempMin!!
                } else {
                    prefs[SimpleDarkAppWidget().currentMinDegreeKey] = 0.001
                }

                if (max != null) {
                    prefs[SimpleDarkAppWidget().currentMaxDegreeKey] =
                        owmResponse.main!!.tempMax!!
                } else {
                    prefs[SimpleDarkAppWidget().currentMaxDegreeKey] = 0.001
                }

                if (iconId != null) {
                    prefs[SimpleDarkAppWidget().currentIconIdKey] = owmResponse.weather[0].id!!
                } else {
                    prefs[SimpleDarkAppWidget().currentIconIdKey] = UI.drawable.clear_day
                }
            }
            widget.update(context, glanceId)
        }
    }
}

class RefreshActionDark : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val widget = SimpleDarkAppWidget()
        val manager = GlanceAppWidgetManager(context)

        val httpResponse: Pair<Pair<HttpStatusCode, HttpStatusCode>, Pair<OwmResponse, WeatherApiResponse>> =
            doHttpResponse(WidgetDI.httpClientStorage)
        val owmResponse: OwmResponse = httpResponse.second.first

        CoroutineScope(Dispatchers.Main).launch {
            SimpleDarkAppWidget().update(
                manager = manager,
                widget = widget,
                context = context,
                owmResponse = owmResponse
            )
        }
    }
}