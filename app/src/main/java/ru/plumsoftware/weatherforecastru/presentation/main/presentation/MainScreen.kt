package ru.plumsoftware.weatherforecastru.presentation.main.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.plumsoftware.weatherforecastru.application.App
import ru.plumsoftware.weatherforecastru.data.utilities.showToast
import ru.plumsoftware.weatherforecastru.presentation.main.store.MainStore
import ru.plumsoftware.weatherforecastru.presentation.main.viewmodel.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel) {

    val state by mainViewModel.state.collectAsState()

    LaunchedEffect(mainViewModel) {
        mainViewModel.label.collect { label ->
            when (label) {
                is MainStore.Label.OpenAuthorization -> {
                    mainViewModel.onOutput(MainViewModel.Output.OpenAuthorizationScreen)
                }

                is MainStore.Label.SkipAuthorization -> {
                    with(label) {
                        mainViewModel.onOutput(
                            MainViewModel.Output.DoHttpResponse(
                                city = city!!
                            )
                        )
                    }
                }
            }
        }
    }

    MainScreen(
        event = mainViewModel::onEvent,
        state = state
    )
}

@Composable
private fun MainScreen(
    event: (MainStore.Intent) -> Unit,
    state: MainStore.State
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}