package ru.plumsoftware.weatherforecast.presentation.main.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import ru.plumsoftware.weatherforecast.presentation.authorization.presentation.AuthorizationScreen
import ru.plumsoftware.weatherforecast.presentation.authorization.component.AuthorizationComponent
import ru.plumsoftware.weatherforecast.ui.WeatherAppTheme

class MainActivity : ComponentActivity() {
    var isDarkTheme: MutableState<Boolean> = mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }

    private fun authorizationOutput(output: AuthorizationComponent.Output) {
        when (output) {
            AuthorizationComponent.Output.OpenLocationScreen -> {

            }

            is AuthorizationComponent.Output.ChangeTheme -> {

            }
        }
    }


    private fun changeTheme(themeValue: Boolean) {
        isDarkTheme.value = themeValue
    }

    @Composable
    internal fun MainContent() {
        val darkTheme: Boolean = isSystemInDarkTheme()

        isDarkTheme = remember {
            mutableStateOf(darkTheme)
        }

        WeatherAppTheme(darkTheme = isDarkTheme.value) {
            Surface {
                AuthorizationScreen(
                    component = AuthorizationComponent(
                        storeFactory = DefaultStoreFactory(),
                        output = ::authorizationOutput
                    )
                )
            }
        }
    }
}
