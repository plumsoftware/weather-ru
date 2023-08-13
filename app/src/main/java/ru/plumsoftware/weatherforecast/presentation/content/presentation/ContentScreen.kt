package ru.plumsoftware.weatherforecast.presentation.content.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.collect
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.presentation.content.presentation.components.CityComponent
import ru.plumsoftware.weatherforecast.presentation.content.presentation.components.WeatherStatus
import ru.plumsoftware.weatherforecast.presentation.content.store.ContentStore
import ru.plumsoftware.weatherforecast.presentation.content.viewmodel.ContentViewModel
import ru.plumsoftware.weatherforecast.ui.SetupUIController

@Composable
fun ContentScreen(contentViewModel: ContentViewModel) {
    val state by contentViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        contentViewModel.label.collect { label ->
            when (label) {
                ContentStore.Label.TodoLabel -> TODO()
            }
        }
    }

    ContentScreen()
}

@Composable
private fun ContentScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = ExtensionPaddingValues._24dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        CityComponent()
        WeatherStatus()
    }
}