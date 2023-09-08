package ru.plumsoftware.weatherforecastru.presentation.aboutapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.plumsoftware.uicomponents.PlumsoftwareIconPack
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.Weather
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.PartlyCloudyDay
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.material.components.TopBar
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionSize
import ru.plumsoftware.weatherforecastru.presentation.aboutapp.store.AboutAppStore
import ru.plumsoftware.weatherforecastru.presentation.aboutapp.viewmodel.AboutAppViewModel

@Composable
fun AboutApp(aboutAppViewModel: AboutAppViewModel) {
    val state by aboutAppViewModel.state.collectAsState()

    LaunchedEffect(aboutAppViewModel) {
        aboutAppViewModel.label.collect { label ->
            when (label) {
                AboutAppStore.Label.BackButtonClicked -> {
                    aboutAppViewModel.onOutput(AboutAppViewModel.Output.OpenSettingsScreen)
                }
            }
        }
    }

    AboutApp(state = state, event = aboutAppViewModel::onEvent)
}

@Composable
private fun AboutApp(state: AboutAppStore.State, event: (AboutAppStore.Intent) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        with(ExtensionPaddingValues) {
//            Back
            Box(modifier = Modifier.padding(all = _24dp)) {
                TopBar(textResId = R.string.about_app,
                    onBackClick = {
                        event(AboutAppStore.Intent.BackButtonClicked)
                    })
            }
            Spacer(modifier = Modifier.height(height = _14dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = _24dp,
                    alignment = Alignment.CenterVertically
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Icon(
                    imageVector = PlumsoftwareIconPack.Weather.PartlyCloudyDay,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = stringResource(id = R.string.weather_logo_description),
                    modifier = Modifier
                        .size(ExtensionSize.IconSize._64dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = _10dp,
                        alignment = Alignment.CenterVertically
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Text(
                        text = state.appName,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "${stringResource(id = R.string.version_hint)} ${state.version}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}