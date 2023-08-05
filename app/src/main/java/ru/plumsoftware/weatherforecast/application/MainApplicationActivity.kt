package ru.plumsoftware.weatherforecast.application

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import ru.plumsoftware.weatherforecast.data.utilities.logd
import ru.plumsoftware.weatherforecast.presentation.authorization.viewmodel.AuthorizationViewModel
import ru.plumsoftware.weatherforecast.presentation.authorization.presentation.AuthorizationScreen
import ru.plumsoftware.weatherforecast.presentation.location.presentation.LocationScreen
import ru.plumsoftware.weatherforecast.presentation.location.viewmodel.LocationViewModel
import ru.plumsoftware.weatherforecast.presentation.main.presentation.MainScreen
import ru.plumsoftware.weatherforecast.presentation.main.viewmodel.MainViewModel
import ru.plumsoftware.weatherforecast.ui.WeatherAppTheme

class MainApplicationActivity : ComponentActivity() {
    private var isDarkTheme = mutableStateOf(false)
    private lateinit var navController: NavHostController
    private val PERMISSION_REQUEST_CODE = 1
    private val permission = Manifest.permission.ACCESS_FINE_LOCATION
    private val permissionGranted = ContextCompat.checkSelfPermission(App.INSTANCE, permission)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        region::View models
        val mainViewModel =
            MainViewModel(
                storeFactory = DefaultStoreFactory(),
                output = { output ->
                    when (output) {
                        is MainViewModel.Output.ChangeTheme -> {
                            isDarkTheme.value = output.isDarkTheme
                        }

                        MainViewModel.Output.OpenAuthorizationScreen -> {
                            navController.navigate(route = Screens.Authorization)
                        }
                    }
                }
            )

        val authorizationModel =
            AuthorizationViewModel(
                storeFactory = DefaultStoreFactory(),
                output = { output ->
                    when (output) {
                        is AuthorizationViewModel.Output.ChangeTheme -> {
                            with(output) {
                                isDarkTheme.value = value
                                mainViewModel.saveThemeInSharedPreferences(theme = value)
                            }
                        }

                        AuthorizationViewModel.Output.OpenLocationScreen -> {
                            if (permissionGranted != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(
                                    MainActivity@ this,
                                    arrayOf(permission),
                                    PERMISSION_REQUEST_CODE
                                )
                            } else {
                                navController.navigate(route = Screens.Location)
                            }
                        }
                    }
                },
                theme = isDarkTheme.value
            )

        val locationViewModel =
            LocationViewModel(
                storeFactory = DefaultStoreFactory(),
                output = { output ->
                    when (output) {
                        LocationViewModel.Output.OpenAuthorizationScreen -> {
                            navController.navigate(route = Screens.Authorization)
                        }

                        is LocationViewModel.Output.OpenContentScreen -> {
                            navController.navigate(route = Screens.Main)
                        }
                    }
                }
            )
//        endregion

        setContent {

            isDarkTheme = remember {
                mutableStateOf(value = mainViewModel.getThemeFromSharedPreferences())
            }

            navController = rememberNavController()

            WeatherAppTheme(darkTheme = isDarkTheme.value) {
                Surface {
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Main
                    ) {
                        composable(route = Screens.Main) {
                            MainScreen(mainViewModel = mainViewModel)
                        }
                        composable(route = Screens.Authorization) {
                            AuthorizationScreen(authorizationViewModel = authorizationModel)
                        }
                        composable(route = Screens.Location) {
                            LocationScreen(locationViewModel = locationViewModel)
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    navController.navigate(route = Screens.Location)
                } else {
                    navController.navigate(route = Screens.Location)
                }
                return
            }
        }
    }
}