package ru.plumsoftware.weatherforecast.presentation.authorization.presentation

import android.provider.Settings.Global.getString
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.presentation.authorization.component.AuthorizationComponent
import ru.plumsoftware.weatherforecast.presentation.authorization.store.AuthorizationStore

@Composable
fun AuthorizationScreen(component: AuthorizationComponent) {

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                AuthorizationStore.Label.AuthorizationSuccess -> {
                    component.onOutput(
                        AuthorizationComponent.Output.OpenLocationScreen
                    )
                }
            }
        }
    }

    AuthorizationScreen(event = component::onEvent)
}

@Composable
private fun AuthorizationScreen(event: (AuthorizationStore.Intent) -> Unit) {
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
                    imageVector = Icons.Default.Person,
                    contentDescription = ""
                )
                Text(
                    text = "Погода",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(_24dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    onClick = {
                        event(AuthorizationStore.Intent.ContinueButtonClicked)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.large)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFED3B9B),
                                    Color(0xFFA8C0FF),
                                    Color(0xFFC071FF)
                                )
                            )
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Начать",
                        style = MaterialTheme.typography.titleSmall.copy(color = Color.White),
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(_8dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = true, onCheckedChange = {})
                    Text(
                        text = "Тёмная тема",
                        style = MaterialTheme.typography.labelMedium.copy(color = Color(0xFFA098AE)),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}
