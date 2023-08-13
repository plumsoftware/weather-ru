package ru.plumsoftware.weatherforecast.presentation.content.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.presentation.content.presentation.components.CityComponent
import ru.plumsoftware.weatherforecast.presentation.content.presentation.components.WeatherStatus
import ru.plumsoftware.weatherforecast.ui.SetupUIController

@Composable
fun ContentScreen() {
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