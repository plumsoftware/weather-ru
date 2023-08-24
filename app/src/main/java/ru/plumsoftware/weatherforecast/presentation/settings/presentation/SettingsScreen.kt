package ru.plumsoftware.weatherforecast.presentation.settings.presentation

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.plumsoftware.uicomponents.PlumsoftwareIconPack
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.Settings
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.settings.Darkmode
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecast.application.App
import ru.plumsoftware.weatherforecast.data.utilities.showToast
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.material.components.BackArrowButton
import ru.plumsoftware.weatherforecast.material.components.TopBar
import ru.plumsoftware.weatherforecast.presentation.settings.viewmodel.SettingsViewModel
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionSize
import ru.plumsoftware.weatherforecast.presentation.location.store.LocationStore
import ru.plumsoftware.weatherforecast.presentation.location.viewmodel.LocationViewModel
import ru.plumsoftware.weatherforecast.presentation.settings.store.SettingsStore
import kotlin.reflect.KFunction1

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    val state by settingsViewModel.state.collectAsState()

    LaunchedEffect(settingsViewModel) {
        settingsViewModel.label.collect { label ->
            when (label) {
                SettingsStore.Label.AboutApp -> {}
                SettingsStore.Label.BackButtonClicked -> {
                    settingsViewModel.onOutput(
                        SettingsViewModel.Output.BackStackClicked
                    )
                }

                SettingsStore.Label.ChangeWeatherUnits -> {}
                SettingsStore.Label.ChangeWindUnits -> {}
                SettingsStore.Label.LeaveFeedBack -> {}
                SettingsStore.Label.Share -> {}
                is SettingsStore.Label.ChangeTheme -> {
                    settingsViewModel.onOutput(SettingsViewModel.Output.ChangedTheme(value = label.value))
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
//                    Units
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Единицы измерения", //TODO
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
                                    text = "Погода", // TODO
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.weight(weight = 1.0f)
                                )
                                Button(
                                    onClick = { /*TODO*/ }
                                ) {
                                    Text(
                                        text = "c".uppercase()
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
                                    text = "Скорость ветра", //TODO()
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.weight(weight = 1.0f)
                                )
                                Button(
                                    onClick = { /*TODO*/ }
                                ) {
                                    Text(
                                        text = "м/с".uppercase()
                                    )
                                }
                            }
                        }
                    }
                }

//                    Application
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Приложение",
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
                                    contentDescription = "application theme logo" //TODO(replace this)
                                )
                                Text(
                                    text = "Тёмная тема", //TODO(replace this)
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.weight(weight = 1.0f)
                                )
                                Checkbox(
                                    checked = state.checkBoxValue,
                                    onCheckedChange = {
                                        event(SettingsStore.Intent.CheckBoxValue(value = it))
                                    })
                            }


//                                About app
                            Button(
                                onClick = { /*TODO*/ },
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
                                        contentDescription = "about application" //TODO(replace this)
                                    )
                                    Text(
                                        text = "О приложении", //TODO(replace this)
                                        style = MaterialTheme.typography.labelMedium,
                                        modifier = Modifier.weight(weight = 1.0f)
                                    )
                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowRight,
                                        contentDescription = "arrow" //TODO(replace this)
                                    )
                                }
                            }

//                                Share application
                            Button(
                                onClick = { /*TODO*/ },
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
                                        contentDescription = "share application" //TODO(replace this)
                                    )
                                    Text(
                                        text = "Поделиться", //TODO(replace this)
                                        style = MaterialTheme.typography.labelMedium,
                                        modifier = Modifier.weight(weight = 1.0f)
                                    )
                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowRight,
                                        contentDescription = "arrow" //TODO(replace this)
                                    )
                                }
                            }
                        }
                    }
                }

//                    Feedback
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Обратная связь",
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
                                onClick = { /*TODO*/ },
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
                                        modifier = Modifier.size(width = 24.dp, height = 23.29.dp),
                                        contentDescription = "RuStore logo" //TODO(replace this)
                                    )
                                    Text(
                                        text = "Отзыв в RuStore", //TODO(replace this)
                                        style = MaterialTheme.typography.labelMedium,
                                        modifier = Modifier.weight(weight = 1.0f)
                                    )
                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowRight,
                                        contentDescription = "arrow" //TODO(replace this)
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