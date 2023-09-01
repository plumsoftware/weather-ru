package ru.plumsoftware.weatherforecast.presentation.authorization.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.plumsoftware.uicomponents.PlumsoftwareIconPack
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.Weather
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.PartlyCloudyDay
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecast.application.App
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.presentation.authorization.presentation.components.PrimaryButton
import ru.plumsoftware.weatherforecast.presentation.authorization.viewmodel.AuthorizationViewModel
import ru.plumsoftware.weatherforecast.presentation.authorization.store.AuthorizationStore

@Composable
fun AuthorizationScreen(authorizationViewModel: AuthorizationViewModel) {

    val state by authorizationViewModel.state.collectAsState()

    LaunchedEffect(authorizationViewModel) {
        authorizationViewModel.label.collect { label ->
            when (label) {
                AuthorizationStore.Label.AuthorizationSuccess -> {
                    authorizationViewModel.onOutput(
                        AuthorizationViewModel.Output.OpenLocationScreen
                    )
                }

                is AuthorizationStore.Label.ThemeChanged -> {
                    authorizationViewModel.onOutput(
                        AuthorizationViewModel.Output.ChangeTheme(state.checkBoxValue)
                    )
                }
            }
        }
    }

    AuthorizationScreen(
        event = authorizationViewModel::onEvent,
        state = state
    )
}

@Composable
private fun AuthorizationScreen(
    event: (AuthorizationStore.Intent) -> Unit,
    state: AuthorizationStore.State
) {
    with(ExtensionPaddingValues) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues(horizontal = _16dp, vertical = _24dp))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = PlumsoftwareIconPack.Weather.PartlyCloudyDay,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = App.INSTANCE.getString(R.string.weather_logo_description),
                    modifier = Modifier
                        .size(64.dp)
                )
                Text(
                    text = App.INSTANCE.getString(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(_24dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                PrimaryButton(onClick = { event(AuthorizationStore.Intent.ContinueButtonClicked) }) {
                    Text(
                        text = App.INSTANCE.getString(R.string.get_started),
                        style = MaterialTheme.typography.titleSmall.copy(color = Color.White),
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(_8dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = state.checkBoxValue,
                        onCheckedChange = { value ->
                            event(AuthorizationStore.Intent.CheckBoxChanged(value))
                        },
                        modifier = Modifier.clip(MaterialTheme.shapes.extraSmall),
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = Color(0xFFA098AE)
                        )
                    )
                    Text(
                        text = App.INSTANCE.getString(R.string.dark_theme),
                        style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFFA098AE)),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}
