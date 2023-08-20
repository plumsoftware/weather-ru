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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.stopKoin
import ru.plumsoftware.weatherforecast.domain.models.UserSettings
import ru.plumsoftware.weatherforecast.domain.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecast.presentation.authorization.viewmodel.AuthorizationViewModel
import ru.plumsoftware.weatherforecast.presentation.authorization.presentation.AuthorizationScreen
import ru.plumsoftware.weatherforecast.presentation.content.presentation.ContentScreen
import ru.plumsoftware.weatherforecast.presentation.content.viewmodel.ContentViewModel
import ru.plumsoftware.weatherforecast.presentation.location.presentation.LocationScreen
import ru.plumsoftware.weatherforecast.presentation.location.viewmodel.LocationViewModel
import ru.plumsoftware.weatherforecast.presentation.main.presentation.MainScreen
import ru.plumsoftware.weatherforecast.presentation.main.viewmodel.MainViewModel
import ru.plumsoftware.weatherforecast.presentation.settings.presentation.SettingsScreen
import ru.plumsoftware.weatherforecast.presentation.settings.viewmodel.SettingsViewModel
import ru.plumsoftware.weatherforecast.ui.SetupUIController
import ru.plumsoftware.weatherforecast.ui.WeatherAppTheme

class MainApplicationActivity : ComponentActivity(), KoinComponent {
    private val sharedPreferencesStorage by inject<SharedPreferencesStorage>()
    private var isDarkTheme = mutableStateOf(false)
    private lateinit var navController: NavHostController
    private val PERMISSION_REQUEST_CODE = 1
    private val activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            isDarkTheme = remember {
                mutableStateOf(value = sharedPreferencesStorage.get().isDarkTheme)
            }
            navController = rememberNavController()

//        region::View model
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

                            is MainViewModel.Output.OpenContentScreen -> {
                                navController.navigate(route = Screens.Content)
                            }
                        }
                    },
                    city = sharedPreferencesStorage.get().city!!
                )

            val authorizationViewModel = AuthorizationViewModel(
                storeFactory = DefaultStoreFactory(),
                output = { output ->
                    when (output) {
                        is AuthorizationViewModel.Output.ChangeTheme -> {
                            with(output) {
                                isDarkTheme.value = value
                                sharedPreferencesStorage.save(
                                    userSettings = UserSettings(
                                        isDarkTheme = value
                                    )
                                )
                            }
                        }

                        AuthorizationViewModel.Output.OpenLocationScreen -> {
                            if (ContextCompat.checkSelfPermission(
                                    App.INSTANCE,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                ActivityCompat.requestPermissions(
                                    activity,
                                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
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
//        endregion

            WeatherAppTheme(darkTheme = isDarkTheme.value) {
                SetupUIController()
                Surface {
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Main
                    ) {
                        composable(route = Screens.Main) {
                            MainScreen(mainViewModel = mainViewModel)
                        }
                        composable(route = Screens.Authorization) {
                            AuthorizationScreen(
                                authorizationViewModel = authorizationViewModel
                            )
                        }
                        composable(route = Screens.Location) {
                            LocationScreen(
                                locationViewModel = LocationViewModel(
                                    storeFactory = DefaultStoreFactory(),
                                    output = { output ->
                                        when (output) {
                                            LocationViewModel.Output.BackStackClicked -> {
                                                navController.popBackStack()
                                            }

                                            is LocationViewModel.Output.OpenContentScreen -> {
                                                navController.navigate(route = Screens.Content)
                                                {
                                                    popUpTo(route = Screens.Content) {
                                                        inclusive = true
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    sharedPreferencesStorage = sharedPreferencesStorage
                                )
                            )
                        }
                        composable(route = Screens.Content) {
                            ContentScreen(
                                contentViewModel = ContentViewModel(
                                    storeFactory = DefaultStoreFactory(),
                                    sharedPreferencesStorage = sharedPreferencesStorage,
                                    output = { output ->
                                        when (output) {
                                            is ContentViewModel.Output.OpenLocationScreen -> {
                                                navController.navigate(route = Screens.Location)
                                            }

                                            is ContentViewModel.Output.OpenSettingsScreen -> {
                                                navController.navigate(route = Screens.Settings)
                                            }
                                        }
                                    }
                                )
                            )
                        }
                        composable(route = Screens.Settings) {
                            SettingsScreen(
                                settingsViewModel = SettingsViewModel()
                            )
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

    override fun onBackPressed() {
        super.onBackPressed()
        when (navController.currentDestination!!.route){
            Screens.Main -> {
                finish()
            }
//            Screens.Location -> {
//                navController.popBackStack()
//            }
//            Screens.Content -> {
//                finish()
//            }
//            Screens.Authorization -> {
//                navController.popBackStack()
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }
}