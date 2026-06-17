package ru.plumsoftware.weatherforecastru.application.shortcut.widgetconfig

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import ru.plumsoftware.weatherforecastru.messanging.WeatherNotificationDependencies
import ru.plumsoftware.weatherforecastru.presentation.ui.SetupUIController
import ru.plumsoftware.weatherforecastru.presentation.ui.WeatherAppTheme
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation.WidgetConfig
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.viewmodel.WidgetConfigViewModel

class WidgetConfigActivity : ComponentActivity() {

    private val sharedPreferencesStorage by lazy {
        WeatherNotificationDependencies.sharedPreferencesStorage(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val widgetConfigViewModel = WidgetConfigViewModel(
                storeFactory = DefaultStoreFactory(),
                sharedPreferencesStorage = sharedPreferencesStorage,
                appContext = applicationContext,
                output = { output ->
                    when (output) {
                        WidgetConfigViewModel.Output.BackStackClicked -> finish()
                    }
                },
            )

            WeatherAppTheme {
                SetupUIController(darkTheme = isSystemInDarkTheme())
                WidgetConfig(
                    widgetConfigViewModel = widgetConfigViewModel,
                )
            }
        }
    }
}
