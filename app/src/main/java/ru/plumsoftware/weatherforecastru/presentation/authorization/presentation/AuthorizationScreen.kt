package ru.plumsoftware.weatherforecastru.presentation.authorization.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ru.plumsoftware.weatherforecastru.presentation.authorization.viewmodel.AuthorizationViewModel
import ru.plumsoftware.weatherforecastru.presentation.authorization.store.AuthorizationStore

@Composable
fun AuthorizationScreen(authorizationViewModel: AuthorizationViewModel) {
    LaunchedEffect(authorizationViewModel) {
        authorizationViewModel.label.collect { label ->
            when (label) {
                AuthorizationStore.Label.AuthorizationSuccess -> {
                    authorizationViewModel.onOutput(
                        AuthorizationViewModel.Output.OpenLocationScreen
                    )
                }
            }
        }
    }

    WelcomeScreen(
        onGetStarted = {
            authorizationViewModel.onEvent(AuthorizationStore.Intent.ContinueButtonClicked)
        }
    )
}
