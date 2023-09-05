package ru.plumsoftware.weatherforecast.application

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.google.gson.Gson
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.MobileAds
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration
import com.yandex.mobile.ads.nativeads.NativeBulkAdLoadListener
import com.yandex.mobile.ads.nativeads.NativeBulkAdLoader
import io.ktor.http.HttpStatusCode
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plumsoftware.weatherforecast.data.models.location.LocationItemDao
import ru.plumsoftware.weatherforecast.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecast.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.weatherforecast.data.utilities.logd
import ru.plumsoftware.weatherforecast.domain.remote.dto.either.WeatherEither
import ru.plumsoftware.weatherforecast.domain.storage.HttpClientStorage
import ru.plumsoftware.weatherforecast.domain.storage.LocationStorage
import ru.plumsoftware.weatherforecast.domain.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecast.presentation.airquality.presentation.AirQualityScreen
import ru.plumsoftware.weatherforecast.presentation.airquality.viewmodel.AirQualityViewModel
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
            val WEATHER_API_VALUE = remember { mutableStateOf(WeatherApiResponse()) }
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
                    CoroutineScope(Dispatchers.Main).launch {
                        navController.navigate(route = Screens.Location)
                    }
                }
            }
            val list = remember {
                mutableStateOf(mutableListOf<NativeAd>())
            }
            val adsError = remember {
                mutableStateOf(false)
            }
            val isAdsLoading = remember {
                mutableStateOf(true)
            }

//            endregion

//            region::Coroutines
            LaunchedEffect(Unit) {
                coroutine.launch {

                    MobileAds.initialize(context) {
                        logd(message = "RSY initialized!")
                    }

                    isAdsLoading.value = true
                    val nativeAdsLoader = NativeBulkAdLoader(context).apply {
                        setNativeBulkAdLoadListener(object : NativeBulkAdLoadListener {
                            override fun onAdsLoaded(p0: MutableList<NativeAd>) {
                                list.value = p0
                                adsError.value = false
                                isAdsLoading.value = false
                            }

                            override fun onAdsFailedToLoad(p0: AdRequestError) {
                                logd(p0.toString())
                                adsError.value = true
                                isAdsLoading.value = false
                            }
                        })
                    }
                    val adRequestConfiguration =
                        NativeAdRequestConfiguration.Builder("demo-native-content-yandex")
                            .build()
                    nativeAdsLoader.loadAds(adRequestConfiguration, 1)

                    OWM_VALUE.value =
                        doHttpResponse(
                            httpClientStorage = httpClientStorage
                        ).first
                    WEATHER_API_VALUE.value = doHttpResponse(
                        httpClientStorage = httpClientStorage
                    ).second
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
                                                        OWM_VALUE.value = doHttpResponse(
                                                            httpClientStorage = httpClientStorage
                                                        ).first
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
                                    weatherApiResponse = WEATHER_API_VALUE.value,
                                    adsList = list.value,
                                    isAdsLoading = isAdsLoading.value,
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

                                            is ContentViewModel.Output.OpenAirQualityScreen -> {
                                                navController.navigate(route = Screens.AirQuality)
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
                                                        doHttpResponse(
                                                            httpClientStorage = httpClientStorage
                                                        ).first
                                                }
                                            }
                                        }
                                    }
                                )
                            )
                        }
                        composable(route = Screens.AirQuality) {
                            AirQualityScreen (
                                airQualityViewModel = AirQualityViewModel(
                                    storeFactory = DefaultStoreFactory(),
                                    airQuality = WEATHER_API_VALUE.value.current!!.airQuality!!,
                                    output = { output ->
                                        when(output){
                                            AirQualityViewModel.Output.OpenContentScreen -> {
                                                navController.popBackStack()
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

    private inline fun <reified T> convertStringToJson(jsonString: String): T =
        Gson().fromJson(jsonString, T::class.java)

    private suspend fun doHttpResponse(
        httpClientStorage: HttpClientStorage
    ): Pair<OwmResponse, WeatherApiResponse> {
        var weatherEither: WeatherEither<String, HttpStatusCode, GMTDate> =
            httpClientStorage.get()
        val owmResponse = convertStringToJson<OwmResponse>(jsonString = weatherEither.data)

        weatherEither = httpClientStorage.getWeatherApi()
        val weatherApiResponse =
            convertStringToJson<WeatherApiResponse>(jsonString = weatherEither.data)
        return Pair(first = owmResponse, second = weatherApiResponse)
    }
//    endregion
}