package ru.plumsoftware.weatherforecast.presentation.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import ru.plumsoftware.weatherforecast.presentation.authorization.presentation.AuthorizationScreen
import ru.plumsoftware.weatherforecast.presentation.authorization.component.AuthorizationComponent
import ru.plumsoftware.weatherforecast.ui.WeatherAppTheme

class MainActivity : ComponentActivity() {
    private var isDarkTheme: MutableState<Boolean> = mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            isDarkTheme = remember { mutableStateOf(true) }
            MainContent(isDarkTheme.value)
        }
    }

    private fun authorizationOutput(output: AuthorizationComponent.Output) {
        when (output) {
            AuthorizationComponent.Output.OpenLocationScreen -> {

            }

            is AuthorizationComponent.Output.ChangeTheme -> {
                isDarkTheme.value = output.value
            }
        }
    }

    @Composable
    internal fun MainContent(isDarkTheme: Boolean) {
        WeatherAppTheme(darkTheme = isDarkTheme) {
            Surface {
                AuthorizationScreen(
                    component = AuthorizationComponent(
                        storeFactory = DefaultStoreFactory(),
                        output = ::authorizationOutput,
                        theme = isDarkTheme
                    )
                )
            }
        }
    }
}
