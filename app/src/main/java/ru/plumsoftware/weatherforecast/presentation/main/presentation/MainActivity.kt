package ru.plumsoftware.weatherforecast.presentation.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import ru.plumsoftware.weatherforecast.application.Screens
import ru.plumsoftware.weatherforecast.presentation.authorization.presentation.AuthorizationScreen
import ru.plumsoftware.weatherforecast.presentation.authorization.model.AuthorizationModel
import ru.plumsoftware.weatherforecast.presentation.location.presentation.LocationScreen
import ru.plumsoftware.weatherforecast.presentation.location.viewmodel.LocationViewModel
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

            val navController = rememberNavController()
            isDarkTheme =
                remember { mutableStateOf(value = mainViewModel.getThemeFromSharedPreferences()) }

            WeatherAppTheme(darkTheme = isDarkTheme.value) {
                Surface {
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Authorization
                    ) {
                        composable(route = Screens.Authorization) {
                            AuthorizationScreen(
                                authorizationModel = AuthorizationModel(
                                    storeFactory = DefaultStoreFactory(),
                                    output = { output ->
                                        when (output) {
                                            AuthorizationModel.Output.OpenLocationScreen -> {
                                                navController.navigate(route = Screens.Location)
                                            }

                                            is AuthorizationModel.Output.ChangeTheme -> {
                                                with(output.value) {
                                                    isDarkTheme.value = this@with
                                                    mainViewModel.saveThemeInSharedPreferences(this@with)
                                                }
                                            }
                                        }
                                    },
                                    theme = isDarkTheme.value
                                )
                            )
                        }
                        composable(route = Screens.Location) {
                            LocationScreen(
                                locationViewModel = LocationViewModel(
                                    storeFactory = DefaultStoreFactory(),
                                    output = { output ->
                                        when (output) {
                                            is LocationViewModel.Output.OpenContentScreen -> TODO()
                                            is LocationViewModel.Output.OpenAuthorizationScreen -> {
                                                navController.navigate(
                                                    route = Screens.Authorization
                                                )
                                            }
                                        }
                                    }
                                )
                            )
                        }
                    }
                }
            }
        }
    }
//    endregion
}
