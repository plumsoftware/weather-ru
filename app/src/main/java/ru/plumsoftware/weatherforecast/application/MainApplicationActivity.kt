package ru.plumsoftware.weatherforecast.application

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecast.data.models.location.LocationItemDao
import ru.plumsoftware.weatherforecast.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecast.data.repository.OwmRepositoryImpl
import ru.plumsoftware.weatherforecast.data.utilities.logd
import ru.plumsoftware.weatherforecast.data.utilities.showToast
import ru.plumsoftware.weatherforecast.domain.constants.Constants
import ru.plumsoftware.weatherforecast.domain.models.settings.WeatherUnits
import ru.plumsoftware.weatherforecast.domain.remote.dto.either.OwmEither
import ru.plumsoftware.weatherforecast.domain.repository.OwmRepository
import ru.plumsoftware.weatherforecast.domain.storage.HttpClientStorage
import ru.plumsoftware.weatherforecast.domain.storage.LocationStorage
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
import ru.plumsoftware.weatherforecast.presentation.ui.SetupUIController
import ru.plumsoftware.weatherforecast.presentation.ui.WeatherAppTheme

class MainApplicationActivity : ComponentActivity(), KoinComponent {
    private var isDarkTheme = mutableStateOf(false)
    private lateinit var navController: NavHostController

    //    region:Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

//            region::Variables
            val sharedPreferencesStorage by inject<SharedPreferencesStorage>()
            val locationItemDao by inject<LocationItemDao>()
            val locationStorage by inject<LocationStorage>()
            val httpClientStorage by inject<HttpClientStorage>()
            val context = LocalContext.current

            isDarkTheme =
                remember { mutableStateOf(value = sharedPreferencesStorage.get().isDarkTheme) }
            navController = rememberNavController()
            val coroutine = rememberCoroutineScope()
            val OWM_VALUE = remember { mutableStateOf(OwmResponse()) }
            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission Accepted: Do something
                    CoroutineScope(Dispatchers.IO).launch {
                        val location = locationStorage.get()
                        sharedPreferencesStorage.saveLocation(location = location)
                        CoroutineScope(Dispatchers.Main).launch {
                            navController.navigate(route = Screens.Location)
                        }
                    }
                } else {
                    // Permission Denied: Do something
                    navController.navigate(route = Screens.Location)
                }
            }

//            endregion

//            region::Coroutines
            LaunchedEffect(Unit) {
                coroutine.launch {
                    OWM_VALUE.value = doHttpResponse(httpClientStorage = httpClientStorage)
                }
            }
//            endregion

            WeatherAppTheme(darkTheme = isDarkTheme.value) {
                SetupUIController()
                Surface {
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Main
                    ) {
                        composable(route = Screens.Main) {
                            MainScreen(
                                mainViewModel = MainViewModel(
                                    storeFactory = DefaultStoreFactory(),
                                    output = { output ->
                                        when (output) {
                                            is MainViewModel.Output.OpenAuthorizationScreen -> {
                                                navController.navigate(route = Screens.Authorization)
                                            }

                                            is MainViewModel.Output.OpenContentScreen -> {
                                                navController.navigate(route = Screens.Content)
                                            }
                                        }
                                    },
                                    city = sharedPreferencesStorage.get().city!!,
                                    httpClientStorage = httpClientStorage
                                )
                            )
                        }
                        composable(route = Screens.Authorization) {
                            AuthorizationScreen(
                                authorizationViewModel = AuthorizationViewModel(
                                    storeFactory = DefaultStoreFactory(),
                                    output = { output ->
                                        when (output) {
                                            is AuthorizationViewModel.Output.ChangeTheme -> {
                                                with(output) {
                                                    isDarkTheme.value = value
                                                    sharedPreferencesStorage.saveAppTheme(
                                                        applicationTheme = isDarkTheme.value
                                                    )
                                                }
                                            }

                                            AuthorizationViewModel.Output.OpenLocationScreen -> {
                                                if (checkLocationPermission()) {
                                                    launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                                } else {
                                                    navController.navigate(route = Screens.Location)
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
                                            LocationViewModel.Output.BackStackClicked -> {
                                                navController.popBackStack()
                                            }

                                            is LocationViewModel.Output.OpenContentScreen -> {
                                                navController.navigate(route = Screens.Content)
                                                {
                                                    coroutine.launch {
                                                        OWM_VALUE.value = doHttpResponse(httpClientStorage = httpClientStorage)
                                                    }

                                                    popUpTo(route = Screens.Content) {
                                                        inclusive = true
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    sharedPreferencesStorage = sharedPreferencesStorage,
                                    locationItemDao = locationItemDao
                                )
                            )
                        }
                        composable(route = Screens.Content) {
                            ContentScreen(
                                contentViewModel = ContentViewModel(
                                    storeFactory = DefaultStoreFactory(),
                                    sharedPreferencesStorage = sharedPreferencesStorage,
                                    owmResponse = OWM_VALUE.value,
                                    output = { output ->
                                        when (output) {
                                            is ContentViewModel.Output.OpenLocationScreen -> {
                                                if (checkLocationPermission()) {
                                                    launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                                } else {
                                                    navController.navigate(route = Screens.Location)
                                                }
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
                                settingsViewModel = SettingsViewModel(
                                    storeFactory = DefaultStoreFactory(),
                                    sharedPreferencesStorage = sharedPreferencesStorage,
                                    output = { output ->
                                        when (output) {
                                            is SettingsViewModel.Output.BackStackClicked -> {
                                                navController.popBackStack()
                                            }

                                            is SettingsViewModel.Output.ChangedTheme -> {
                                                isDarkTheme.value = output.value
                                            }

                                            is SettingsViewModel.Output.OnSettingsChange -> {
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    OWM_VALUE.value =
                                                        doHttpResponse(httpClientStorage = httpClientStorage)
                                                }
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

    override fun onBackPressed() {
        super.onBackPressed()
        when (navController.currentDestination!!.route) {
            Screens.Main -> {
                finish()
            }
        }
    }
//    endregion

    //    region::Private function
    private fun checkLocationPermission(): Boolean = ContextCompat.checkSelfPermission(
        App.INSTANCE.applicationContext,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED

    private fun convertStringToJson(jsonString: String): OwmResponse =
        Gson().fromJson(jsonString, OwmResponse::class.java)

    private suspend fun doHttpResponse(httpClientStorage: HttpClientStorage): OwmResponse {
        val owmEither: OwmEither<String, HttpStatusCode, GMTDate> =
            httpClientStorage.get()
        val owmResponse = convertStringToJson(owmEither.data)
        return owmResponse
    }

    private fun windDirection(deg: Int): String {
        val directions = arrayOf(
            "С", "ССВ", "СВ", "ВСВ",
            "В", "ВЮВ", "ЮВ", "ЮЮВ",
            "Ю", "ЮЮЗ", "ЮЗ", "ЗЮЗ",
            "З", "ЗСЗ", "СЗ", "ССЗ"
        )
        val index = ((deg / 22.5) + 0.5).toInt() % 16
        return directions[index]
    }

    private fun windDirectionFull(deg: Int): String {
        val directions = arrayOf(
            "Севера", "Северо-северо-востока", "Северо-востока", "Восток-северо-востока",
            "Востока", "Восток-юго-востока", "Юго-востока", "Юго-юго-востока",
            "Юга", "Юго-юго-запада", "Юго-запада", "Запад-юго-запада",
            "Запада", "Запад-северо-запада", "Северо-запада", "Северо-северо-запада"
        )
        val index = ((deg / 22.5) + 0.5).toInt() % 16
        return directions[index]
    }
//    endregion
}