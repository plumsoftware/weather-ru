package ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.material.components.TopBar
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation.components.SliderItem
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation.components.WidgetPreview
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation.components.uiblue.theme.AppThemeBlue
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation.components.uigreen.theme.AppThemeGreen
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation.components.uired.theme.AppThemeRed
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.store.WidgetConfigStore
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.viewmodel.WidgetConfigViewModel
import ru.plumsoftware.weatherforecastru.utilites.getWallpaper

@Composable
fun WidgetConfig(widgetConfigViewModel: WidgetConfigViewModel) {
    val state by widgetConfigViewModel.state.collectAsState()

    LaunchedEffect(key1 = widgetConfigViewModel) {
        widgetConfigViewModel.label.collect { label ->
            when (label) {
                WidgetConfigStore.Label.BackButtonClicked -> widgetConfigViewModel.onOutput(
                    WidgetConfigViewModel.Output.BackStackClicked
                )
            }
        }
    }

    WidgetConfig(
        event = widgetConfigViewModel::onEvent,
        state = state
    )
}

@Composable
fun WidgetConfig(
    event: (WidgetConfigStore.Intent) -> Unit,
    state: WidgetConfigStore.State
) {

    val context = LocalContext.current
    val currentVersion by produceState(initialValue = android.os.Build.VERSION.SDK_INT) {
        value = android.os.Build.VERSION.SDK_INT
    }

    val minSupportedVersion = 31

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(all = ExtensionPaddingValues._24dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        with(ExtensionPaddingValues) {
//            Back
            TopBar(
                showBack = true,
                textResId = R.string.widget_hint,
                onBackClick = {
                    event(WidgetConfigStore.Intent.BackButtonClicked)
                })

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.medium)
                    .fillMaxSize()
            ) {
                Image(
                    bitmap = getWallpaper(context = context),
                    contentDescription = stringResource(id = R.string.wallpaper_image_hint),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(shape = MaterialTheme.shapes.medium)
                        .fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = _10dp, end = _10dp, bottom = _10dp, top = _34dp)
                ) {

                    WidgetPreview(
                        corners = state.radius,
                        background = Color(
                            red = state.red,
                            green = state.green,
                            blue = state.blue
                        )
                    )

                    Spacer(modifier = Modifier.height(height = _34dp))

                    Card(
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = _10dp)
                            .align(alignment = Alignment.BottomCenter)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                space = _10dp,
                                alignment = Alignment.CenterVertically
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = _24dp)
                                .wrapContentHeight()
                                .verticalScroll(state = rememberScrollState())
                        ) {

//                            radius
                            if (currentVersion >= minSupportedVersion) {
                                SliderItem(
                                    title = stringResource(id = R.string.radius),
                                    startValue = state.radius.toFloat(),
                                    range = 0f..32f,
                                    onValueChange = { value: Float ->
                                        event(WidgetConfigStore.Intent.RadiusChanged(value = value.toInt()))
                                    },
                                    onValueChangeFinished = {
                                        event(WidgetConfigStore.Intent.Save)
                                    }
                                )
                            } else {
                                Text(
                                    text = stringResource(id = R.string.radius_settings),
                                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.outline)
                                )
                                event(WidgetConfigStore.Intent.RadiusChanged(value = 0))
                            }

//                            colors
                            AppThemeRed {
                                SliderItem(
                                    title = stringResource(id = R.string.color),
                                    startValue = state.red.toFloat(),
                                    range = 0f..255f,
                                    onValueChange = { value: Float ->
                                        event(WidgetConfigStore.Intent.RedChanged(value = value.toInt()))
                                    },
                                    onValueChangeFinished = {
                                        event(WidgetConfigStore.Intent.Save)
                                    }
                                )
                            }

                            AppThemeGreen {
                                SliderItem(
                                    title = "",
                                    startValue = state.green.toFloat(),
                                    range = 0f..255f,
                                    onValueChange = { value: Float ->
                                        event(WidgetConfigStore.Intent.GreenChanged(value = value.toInt()))
                                    },
                                    onValueChangeFinished = {
                                        event(WidgetConfigStore.Intent.Save)
                                    }
                                )
                            }

                            AppThemeBlue {
                                SliderItem(
                                    title = "",
                                    startValue = state.blue.toFloat(),
                                    range = 0f..255f,
                                    onValueChange = { value: Float ->
                                        event(WidgetConfigStore.Intent.BlueChanged(value = value.toInt()))
                                    },
                                    onValueChangeFinished = {
                                        event(WidgetConfigStore.Intent.Save)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
