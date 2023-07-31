package ru.plumsoftware.weatherforecast.presentation.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import ru.plumsoftware.weatherforecast.presentation.authorization.presentation.AuthorizationScreen
import ru.plumsoftware.weatherforecast.presentation.authorization.component.AuthorizationComponent
import ru.plumsoftware.weatherforecast.presentation.main.viewmodel.MainViewModel
import ru.plumsoftware.weatherforecast.ui.WeatherAppTheme

class MainActivity : ComponentActivity() {
    //    region::View model
    private val mainViewModel: MainViewModel = MainViewModel(context = this)
//    endregion

    //    region::Variables
    private var isDarkTheme: MutableState<Boolean> = mutableStateOf(value = false)
//    endregion


    //    region::Functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            isDarkTheme =
                remember { mutableStateOf(value = mainViewModel.getThemeFromSharedPreferences()) }
            MainContent(isDarkTheme.value)
        }
    }

    private fun authorizationOutput(output: AuthorizationComponent.Output) {
        when (output) {
            AuthorizationComponent.Output.OpenLocationScreen -> {

            }

            is AuthorizationComponent.Output.ChangeTheme -> {
                with(output.value) {
                    isDarkTheme.value = this@with
                    mainViewModel.saveThemeInSharedPreferences(this@with)
                }
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
//    endregion
}
