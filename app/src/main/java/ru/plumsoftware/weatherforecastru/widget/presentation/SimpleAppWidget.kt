package ru.plumsoftware.weatherforecastru.widget.presentation

import android.content.Context

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
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
import ru.plumsoftware.weatherforecastru.widget.di.WidgetDI
import ru.plumsoftware.weatherforecastru.widget.keys.Keys
import ru.plumsoftware.weatherforecastru.widget.utilites.doHttpResponse
import ru.plumsoftware.weatherforecastru.widget.material.PlumsoftwareWidgetTheme
import ru.plumsoftware.weatherforecastru.widget.utilites.badIconToGoodIcon
import ru.plumsoftware.weatherforecastru.widget.utilites.darkerColor
import ru.plumsoftware.weatherforecastru.widget.utilites.makeColorDarker


class SimpleAppWidget : GlanceAppWidget(), KoinComponent {

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
        val widget = SimpleAppWidget()
//        endregion

        coroutine.launch {
            val httpResponse: Pair<Pair<HttpStatusCode, HttpStatusCode>, Pair<OwmResponse, WeatherApiResponse>> =
                doHttpResponse(WidgetDI.httpClientStorage)
            val owmResponse: OwmResponse = httpResponse.second.first

            update(manager = manager, widget = widget, context = context, owmResponse = owmResponse)
        }

        with(WidgetDI.sharedPreferencesStorage.getWidget()) {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(
                        colorProvider = ColorProvider(
                            Color(
                                red = red,
                                green = green,
                                blue = blue
                            )
                        )
                    )
                    .cornerRadius(radius = radius.dp)
                    .clickable(onClick = actionRunCallback<RefreshAction>())
                    .appWidgetBackground(),
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
                            colorFilter = ColorFilter.tint(
                                ColorProvider(
                                    darkerColor(
                                        color = Color(red = red, green = green, blue = blue)
                                    )
                                )
                            ),
                        )
                        Spacer(modifier = GlanceModifier.width(width = ExtensionPaddingValues._10dp))
                        Column(
                            modifier = GlanceModifier,
                            horizontalAlignment = Alignment.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${currentDegree.toInt()}°",
                                style = PlumsoftwareWidgetTheme.PlumsoftwareWidgetStyle.bold.copy(
                                    color = ColorProvider(
                                        darkerColor(
                                            color = Color(red = red, green = green, blue = blue)
                                        )
                                    )
                                )
                            )
                            Row(
                                modifier = GlanceModifier,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${currentMinDegree.toInt()}°",
                                    style = PlumsoftwareWidgetTheme.PlumsoftwareWidgetStyle.regular.copy(
                                        color = ColorProvider(
                                            makeColorDarker(
                                                color = Color(red = red, green = green, blue = blue)
                                            )
                                        )
                                    )
                                )
                                Spacer(modifier = GlanceModifier.width(width = ExtensionPaddingValues._4dp))
                                Text(
                                    text = "${currentMaxDegree.toInt()}°",
                                    style = PlumsoftwareWidgetTheme.PlumsoftwareWidgetStyle.regular.copy(
                                        color = ColorProvider(
                                            darkerColor(
                                                color = Color(red = red, green = green, blue = blue)
                                            )
                                        )
                                    )
                                )
                            }
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
                val current = prefs[SimpleAppWidget().currentDegreeKey]
                val min = prefs[SimpleAppWidget().currentMinDegreeKey]
                val max = prefs[SimpleAppWidget().currentMaxDegreeKey]
                val iconId = prefs[SimpleAppWidget().currentIconIdKey]

                prefs[SimpleAppWidget().currentDegreeKey] = 0.001

                if (current != null) {
                    prefs[SimpleAppWidget().currentDegreeKey] = owmResponse.main!!.temp!!
                } else {
                    prefs[SimpleAppWidget().currentDegreeKey] = 0.001
                }

                if (min != null) {
                    prefs[SimpleAppWidget().currentMinDegreeKey] =
                        owmResponse.main!!.tempMin!!
                } else {
                    prefs[SimpleAppWidget().currentMinDegreeKey] = 0.001
                }

                if (max != null) {
                    prefs[SimpleAppWidget().currentMaxDegreeKey] =
                        owmResponse.main!!.tempMax!!
                } else {
                    prefs[SimpleAppWidget().currentMaxDegreeKey] = 0.001
                }

                if (iconId != null) {
                    prefs[SimpleAppWidget().currentIconIdKey] = owmResponse.weather[0].id!!
                } else {
                    prefs[SimpleAppWidget().currentIconIdKey] = UI.drawable.clear_day
                }
            }
            widget.update(context, glanceId)
        }
    }

    class RefreshAction : ActionCallback {
        override suspend fun onAction(
            context: Context,
            glanceId: GlanceId,
            parameters: ActionParameters
        ) {
            val manager = GlanceAppWidgetManager(context)
            val widget = SimpleAppWidget()

            val httpResponse: Pair<Pair<HttpStatusCode, HttpStatusCode>, Pair<OwmResponse, WeatherApiResponse>> =
                doHttpResponse(WidgetDI.httpClientStorage)
            val owmResponse: OwmResponse = httpResponse.second.first

            CoroutineScope(Dispatchers.Main).launch {
                SimpleAppWidget().update(
                    manager = manager,
                    widget = widget,
                    context = context,
                    owmResponse = owmResponse
                )
            }
        }
    }
}
