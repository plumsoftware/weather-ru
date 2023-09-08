package ru.plumsoftware.weatherforecastru.presentation.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetupUIController() {
    val systemUiController = rememberSystemUiController()
    val color = MaterialTheme.colorScheme.background

    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
        )
        systemUiController.setNavigationBarColor(
            color = color
        )
    }
}