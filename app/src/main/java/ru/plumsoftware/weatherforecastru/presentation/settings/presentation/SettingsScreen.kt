package ru.plumsoftware.weatherforecastru.presentation.settings.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.plumsoftware.uicomponents.PlumsoftwareIconPack
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.Settings
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.settings.Darkmode
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.material.components.TopBar
import ru.plumsoftware.weatherforecastru.presentation.settings.viewmodel.SettingsViewModel
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionSize
import ru.plumsoftware.weatherforecastru.presentation.settings.store.SettingsStore

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    val state by settingsViewModel.state.collectAsState()

    LaunchedEffect(settingsViewModel) {
        settingsViewModel.label.collect { label ->
            when (label) {
                SettingsStore.Label.AboutApp -> {
                    settingsViewModel.onOutput(SettingsViewModel.Output.OpenSetting)
                }

                SettingsStore.Label.BackButtonClicked -> {
                    settingsViewModel.onOutput(
                        SettingsViewModel.Output.BackStackClicked
                    )
                }

                SettingsStore.Label.LeaveFeedBack -> {
                    settingsViewModel.onOutput(SettingsViewModel.Output.LeaveFeedBack)
                }

                SettingsStore.Label.Share -> {
                    settingsViewModel.onOutput(SettingsViewModel.Output.Share)
                }

                is SettingsStore.Label.ChangeTheme -> {
                    settingsViewModel.onOutput(SettingsViewModel.Output.ChangedTheme(value = label.value))
                }

                is SettingsStore.Label.SettingsChange -> {
                    settingsViewModel.onOutput(SettingsViewModel.Output.OnSettingsChange)
                }

                SettingsStore.Label.WidgetConfigureSettings -> {
                    settingsViewModel.onOutput(SettingsViewModel.Output.OpenWidgetConfig)
                }
            }
        }
    }

    SettingsScreen(
        event = settingsViewModel::onEvent,
        state = state
    )
}

@Composable
private fun SettingsScreen(
    event: (SettingsStore.Intent) -> Unit,
    state: SettingsStore.State
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        with(ExtensionPaddingValues) {
//                Back
            Box(modifier = Modifier.padding(all = _24dp)) {
                TopBar(textResId = R.string.settings, onBackClick = {
                    event(SettingsStore.Intent.BackButtonClicked)
                })
            }
            Spacer(modifier = Modifier.height(height = _14dp))

//                Content
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = _24dp,
                    alignment = Alignment.Top
                ),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
                    .weight(weight = 1f, fill = false)
                    .padding(horizontal = _24dp)
            ) {
//                Units
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.weather_units),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .padding(horizontal = _24dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Spacer(
                        modifier = Modifier
                            .height(height = _10dp)
                            .fillMaxWidth()
                    )
                    Card {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                space = _24dp,
                                alignment = Alignment.CenterVertically
                            ),
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .padding(all = _14dp)
                                .fillMaxWidth()
                        ) {

//                                Weather
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = _24dp,
                                    alignment = Alignment.Start
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(id = R.string.weather),
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.weight(weight = 1.0f)
                                )
                                Button(
                                    onClick = {
                                        event(SettingsStore.Intent.ChangeWeatherUnits)
                                    }
                                ) {
                                    Text(
                                        text = state.weatherUnit.unitsPresentation.uppercase()
                                    )
                                }
                            }

//                                Wind speed
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = _24dp,
                                    alignment = Alignment.Start
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(id = R.string.wind_speed),
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.weight(weight = 1.0f)
                                )
                                Button(
                                    onClick = {
                                        event(SettingsStore.Intent.ChangeWindUnits)
                                    }
                                ) {
                                    Text(
                                        text = state.windSpeed.windPresentation.uppercase()
                                    )
                                }
                            }
                        }
                    }
                }

//                    Application
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.application),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .padding(horizontal = _24dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Spacer(
                        modifier = Modifier
                            .height(height = _10dp)
                            .fillMaxWidth()
                    )
                    Card {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {

//                                Dark theme
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = _14dp,
                                    alignment = Alignment.Start
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = _14dp,
                                        end = _14dp,
                                        top = _14dp,
                                        bottom = _14dp
                                    )
                            ) {
                                Icon(
                                    imageVector = PlumsoftwareIconPack.Settings.Darkmode,
                                    modifier = Modifier.size(size = ExtensionSize.IconSize._24dp),
                                    contentDescription = stringResource(id = R.string.application_theme_icon)
                                )
                                Text(
                                    text = stringResource(id = R.string.theme_desc),
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.weight(weight = 1.0f)
                                )
                                Checkbox(
                                    checked = state.checkBoxValue,
                                    onCheckedChange = {
                                        event(SettingsStore.Intent.CheckBoxValue(value = it))
                                    })
                            }

//                            Widget config
                            Button(
                                onClick = {
                                    event(SettingsStore.Intent.WidgetConfigureSettings)
                                },
                                contentPadding = PaddingValues(
                                    horizontal = _14dp,
                                    vertical = _24dp
                                ),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                ),
                                shape = RoundedCornerShape(size = 0.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        space = _14dp,
                                        alignment = Alignment.Start
                                    ),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Build,
                                        contentDescription = stringResource(id = R.string.widget_icon_hint)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.widget_hint),
                                        style = MaterialTheme.typography.labelMedium,
                                        modifier = Modifier.weight(weight = 1.0f)
                                    )
                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowRight,
                                        contentDescription = stringResource(id = R.string.button_arrow)
                                    )
                                }
                            }

//                            About app
                            Button(
                                onClick = {
                                    event(SettingsStore.Intent.AboutApp)
                                },
                                contentPadding = PaddingValues(
                                    horizontal = _14dp,
                                    vertical = _24dp
                                ),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                ),
                                shape = RoundedCornerShape(size = 0.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        space = _14dp,
                                        alignment = Alignment.Start
                                    ),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Info,
                                        contentDescription = stringResource(id = R.string.about_application_icon)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.about_app),
                                        style = MaterialTheme.typography.labelMedium,
                                        modifier = Modifier.weight(weight = 1.0f)
                                    )
                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowRight,
                                        contentDescription = stringResource(id = R.string.button_arrow)
                                    )
                                }
                            }

//                            Share application
                            if (ru.plumsoftware.data.BuildConfig.platform == "RuStore")
                                Button(
                                    onClick = { event(SettingsStore.Intent.Share) },
                                    contentPadding = PaddingValues(
                                        horizontal = _14dp,
                                        vertical = _24dp
                                    ),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                    shape = RoundedCornerShape(size = 0.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(
                                            space = _14dp,
                                            alignment = Alignment.Start
                                        ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Share,
                                            contentDescription = stringResource(id = R.string.share_application)
                                        )
                                        Text(
                                            text = stringResource(id = R.string.share_),
                                            style = MaterialTheme.typography.labelMedium,
                                            modifier = Modifier.weight(weight = 1.0f)
                                        )
                                        Icon(
                                            imageVector = Icons.Rounded.KeyboardArrowRight,
                                            contentDescription = stringResource(id = R.string.button_arrow)
                                        )
                                    }
                                }
                        }
                    }
                }

//                    Feedback
                if (ru.plumsoftware.data.BuildConfig.platform == "RuStore")
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(id = R.string.feedback),
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier
                                .padding(horizontal = _24dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                        Spacer(
                            modifier = Modifier
                                .height(height = _10dp)
                                .fillMaxWidth()
                        )
                        Card {
                            Column(
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {

//                                Feed back
                                Button(
                                    onClick = { event(SettingsStore.Intent.LeaveFeedBack) },
                                    contentPadding = PaddingValues(
                                        horizontal = _14dp,
                                        vertical = _24dp
                                    ),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    ),
                                    shape = RoundedCornerShape(size = 0.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(
                                            space = _14dp,
                                            alignment = Alignment.Start
                                        ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Image(
                                            painter = painterResource(id = ru.plumsoftware.uicomponents.R.drawable.rustore_logo),
                                            modifier = Modifier.size(
                                                width = 24.dp,
                                                height = 23.29.dp
                                            ),
                                            contentDescription = stringResource(id = R.string.rustore_logo)
                                        )
                                        Text(
                                            text = stringResource(id = R.string.rustore_review),
                                            style = MaterialTheme.typography.labelMedium,
                                            modifier = Modifier.weight(weight = 1.0f)
                                        )
                                        Icon(
                                            imageVector = Icons.Rounded.KeyboardArrowRight,
                                            contentDescription = stringResource(id = R.string.button_arrow)
                                        )
                                    }
                                }
                            }
                        }
                    }

                Spacer(modifier = Modifier.height(height = _14dp))
            }
        }
    }
}