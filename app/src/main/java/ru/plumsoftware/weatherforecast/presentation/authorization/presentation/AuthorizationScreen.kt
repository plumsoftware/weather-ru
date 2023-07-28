package ru.plumsoftware.weatherforecast.presentation.authorization.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { event(AuthorizationStore.Intent.ContinueButtonClicked) }) {
            Text(
                text = "Привет!",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
        }
    }
}