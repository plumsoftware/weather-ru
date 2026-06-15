package ru.plumsoftware.weatherforecastru.application

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.yandex.mobile.ads.appopenad.AppOpenAd
import com.yandex.mobile.ads.appopenad.AppOpenAdEventListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoadListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoader
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.common.YandexAds
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeBulkAdLoadListener
import com.yandex.mobile.ads.nativeads.NativeBulkAdLoader
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.plumsoftware.weatherforecast.BuildConfig
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.data.constants.Constants
import ru.plumsoftware.weatherforecastru.data.database.LocationItemDatabase
import ru.plumsoftware.weatherforecastru.data.models.location.LocationItemDao
import ru.plumsoftware.weatherforecastru.data.models.airquality.AirQualityData
import ru.plumsoftware.weatherforecastru.data.repository.AirQualityRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.remote.dto.forecast_owm.MainWeatherResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.Astro
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiAstronomyResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import ru.plumsoftware.weatherforecastru.data.utilities.logd
import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither
import ru.plumsoftware.weatherforecastru.data.repository.LocationRepository
import ru.plumsoftware.weatherforecastru.data.repository.LocationRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.repository.OwmRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository
import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.map.MapGridWeatherRepository
import ru.plumsoftware.weatherforecastru.data.repository.WeatherApiRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.storage.HttpClientStorage
import ru.plumsoftware.weatherforecastru.data.storage.LocationStorage
import ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecastru.data.usecase.location.GetLastKnownLocationUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetFirstUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetNotificationItemUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.GetUserSettingsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveFirstUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveNotificationItemUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsAppThemeUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsLocationUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsShowTipsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsWeatherUnitsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsWindUnitsUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetHourlyUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetOwmUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetWeatherApiAstronomyUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetWeatherApiCurrentUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetWeatherApiForecastUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.widget.GetWidgetConfigUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.widget.SaveWidgetConfigUseCase
import ru.plumsoftware.weatherforecastru.presentation.noconnection.presentation.NoConnection
import ru.plumsoftware.weatherforecastru.presentation.aboutapp.presentation.AboutApp
import ru.plumsoftware.weatherforecastru.presentation.aboutapp.viewmodel.AboutAppViewModel
import ru.plumsoftware.weatherforecastru.presentation.airquality.presentation.AirQualityScreen
import ru.plumsoftware.weatherforecastru.presentation.airquality.viewmodel.AirQualityViewModel
import ru.plumsoftware.weatherforecastru.presentation.authorization.viewmodel.AuthorizationViewModel
import ru.plumsoftware.weatherforecastru.presentation.authorization.presentation.AuthorizationScreen
import ru.plumsoftware.weatherforecastru.presentation.app.WeatherSession
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.ContentScreen
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.hasCurrentWeather
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.resolveWeatherApiCurrent
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.weatherApiForecastHasHourlyData
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.weatherApiForecastDayCount
import ru.plumsoftware.weatherforecastru.presentation.content.viewmodel.ContentViewModel
import ru.plumsoftware.weatherforecastru.presentation.location.presentation.LocationScreen
import ru.plumsoftware.weatherforecastru.presentation.location.viewmodel.LocationViewModel
import ru.plumsoftware.weatherforecastru.presentation.main.presentation.MainScreen
import ru.plumsoftware.weatherforecastru.presentation.main.viewmodel.MainViewModel
import ru.plumsoftware.weatherforecastru.presentation.noconnection.viewmodel.NoConnectionViewModel
import ru.plumsoftware.weatherforecastru.presentation.settings.presentation.SettingsScreen
import ru.plumsoftware.weatherforecastru.presentation.settings.viewmodel.SettingsViewModel
import ru.plumsoftware.weatherforecastru.presentation.ui.SetupUIController
import ru.plumsoftware.weatherforecastru.presentation.ui.WeatherAppTheme
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation.WidgetConfig
import ru.plumsoftware.weatherforecastru.presentation.widgetconfig.viewmodel.WidgetConfigViewModel
import ru.plumsoftware.weatherforecastru.application.permissions.AppPermissionsHelper
import ru.plumsoftware.weatherforecastru.application.permissions.EntryPermissionsFlow
import ru.plumsoftware.weatherforecastru.application.permissions.rememberNotificationSetupPermissionHandler
import ru.plumsoftware.weatherforecastru.messanging.NotificationPeriods
import ru.plumsoftware.weatherforecastru.messanging.WeatherNotificationScheduler
import ru.plumsoftware.weatherforecastru.presentation.settings.store.SettingsStore

class MainApplicationActivity : ComponentActivity() {
    private var isDarkTheme = mutableStateOf(false)
    private lateinit var navController: NavHostController
    private lateinit var analytics: FirebaseAnalytics
    private val appOpenAdEventListener = AdEventListener()
    private var myAppOpenAd: AppOpenAd? = null

    private lateinit var sharedPreferencesStorage: SharedPreferencesStorage

    private val context = this

    //    region:Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        actionBar?.hide()

//        region::Add shortcuts
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            addShortcutApiBefore26(context = this@MainApplicationActivity)
//        } else {
//            addShortcutApiAfter26(context = this@MainApplicationActivity)
//        }
//        endregion

        setContent {

//            region::Variables
            val room = Room.databaseBuilder(
                context,
                LocationItemDatabase::class.java,
                Constants.Database.DATABASE_NAME
            ).build()
            val owmClient = remember {
                HttpClient(CIO) {
                    install(ContentNegotiation) {
                        json(
                            Json {
                                ignoreUnknownKeys = true
                                prettyPrint = true
                                isLenient = true
                            }
                        )
                    }
                    install(HttpTimeout) {
                        requestTimeoutMillis = 15000
                        connectTimeoutMillis = 10000
                        socketTimeoutMillis = 15000
                    }
                }
            }
            val weatherApiClient = remember {
                HttpClient(Android) {
                    engine {
                        connectTimeout = 0
                        socketTimeout = 0
                    }
                    install(ContentNegotiation) {
                        json(
                            Json {
                                ignoreUnknownKeys = true
                                isLenient = true
                            }
                        )
                    }
                }
            }
            DisposableEffect(owmClient, weatherApiClient) {
                onDispose {
                    owmClient.close()
                    weatherApiClient.close()
                }
            }

            val sharedPreferencesRepository = remember {
                SharedPreferencesRepositoryImpl(context = context)
            }

            val locationItemDao = room.dao
            val locationStorage = LocationStorage(
                getLastKnownLocationUseCase = GetLastKnownLocationUseCase(locationRepository = LocationRepositoryImpl(context = context))
            )
            val prefsStorage = remember(sharedPreferencesRepository) {
                SharedPreferencesStorage(
                getUserSettingsUseCase = ru.plumsoftware.weatherforecastru.data.usecase.settings.GetUserSettingsUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                getUserSettingsShowTipsUseCase = GetUserSettingsShowTipsUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                getFirstUseCase = GetFirstUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveUserSettingsUseCase = SaveUserSettingsUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveUserSettingsAppThemeUseCase = SaveUserSettingsAppThemeUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveUserSettingsShowTipsUseCase = ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsShowTipsUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveUserSettingsWeatherUnitsUseCase = SaveUserSettingsWeatherUnitsUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveUserSettingsWindUnitsUseCase = SaveUserSettingsWindUnitsUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveUserSettingsLocationUseCase = ru.plumsoftware.weatherforecastru.data.usecase.settings.SaveUserSettingsLocationUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveFirstUseCase = SaveFirstUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveWidgetConfigUseCase = SaveWidgetConfigUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                getWidgetConfigUseCase = GetWidgetConfigUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                getNotificationItemUseCase = GetNotificationItemUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                ),
                saveNotificationItemUseCase = SaveNotificationItemUseCase(
                    sharedPreferencesRepository = sharedPreferencesRepository
                )
                )
            }
            sharedPreferencesStorage = prefsStorage
            val httpClientStorage = remember(owmClient, weatherApiClient, prefsStorage) {
                val owmRepository = OwmRepositoryImpl(client = owmClient, sharedPreferencesStorage = prefsStorage)
                val weatherApiRepository = WeatherApiRepositoryImpl(
                    client = weatherApiClient,
                    sharedPreferencesStorage = prefsStorage,
                )
                HttpClientStorage(
                    getOwmUseCase = GetOwmUseCase(owmRepository = owmRepository),
                    getWeatherApiCurrentUseCase = GetWeatherApiCurrentUseCase(
                        weatherApiRepository = weatherApiRepository,
                    ),
                    getWeatherApiForecastUseCase = GetWeatherApiForecastUseCase(
                        weatherApiRepository = weatherApiRepository,
                    ),
                    getWeatherApiAstronomyUseCase = GetWeatherApiAstronomyUseCase(
                        weatherApiRepository = weatherApiRepository,
                    ),
                    getHourlyUseCase = GetHourlyUseCase(owmRepository = owmRepository),
                )
            }
            val airQualityRepository = remember(weatherApiClient) {
                AirQualityRepositoryImpl(client = weatherApiClient)
            }
            val context = LocalContext.current
            val sharedDesc = stringResource(id = R.string.share_description)
            val appOpenAdLoader: AppOpenAdLoader = AppOpenAdLoader(application)
            val openAdsRequest = AdRequest.Builder(BuildConfig.OPEN_ADS_ID).build()

            analytics = Firebase.analytics
            isDarkTheme =
                remember { mutableStateOf(value = prefsStorage.get().isDarkTheme) }
            navController = rememberNavController()
            val coroutine = rememberCoroutineScope()
            val weatherSession = remember { WeatherSession() }
            var showWeatherErrorDialog by remember { mutableStateOf(false) }

            suspend fun loadWeatherIntoSession(): Boolean {
                if (!checkInternetConnection(context)) return false
                weatherSession.setLoading(true)
                return try {
                    val result = fetchWeatherData(
                        httpClientStorage = httpClientStorage,
                        weatherSession = weatherSession,
                    )
                    val hasCurrentWeather = result.useOwmForCurrent && result.owmResponse.hasCurrentWeather() ||
                        !result.useOwmForCurrent && result.weatherApiResponse.hasCurrentWeather()
                    if (!hasCurrentWeather) {
                        showWeatherErrorDialog = true
                    }
                    val airQuality = fetchAirQuality(
                        airQualityRepository = airQualityRepository,
                        owmResponse = result.owmResponse,
                        weatherApiResponse = result.weatherApiResponse,
                    )
                    weatherSession.applyWeather(
                        owmResponse = result.owmResponse,
                        owmHourlyResponse = result.owmHourlyResponse,
                        weatherApiResponse = result.weatherApiResponse,
                        astronomyAstro = result.astronomyAstro,
                        airQualityData = airQuality,
                        owmCode = result.owmCode.value,
                        owmHourlyCode = result.owmHourlyCode.value,
                        weatherApiCode = result.weatherApiCode,
                        useOwmForCurrent = result.useOwmForCurrent,
                    )
                    true
                } catch (e: Exception) {
                    logd("Weather load error: ${e.message}")
                    weatherSession.setLoading(false)
                    showWeatherErrorDialog = true
                    true
                }
            }
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
            val launcherReadExtStorage = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission Accepted: Do something
                    CoroutineScope(Dispatchers.IO).launch {
                        CoroutineScope(Dispatchers.Main).launch {
                            navController.navigate(route = Screens.WidgetConfig)
                        }
                    }
                } else {
                    // Permission Denied: Do something
                    CoroutineScope(Dispatchers.Main).launch {

                    }
                }
            }
            var entryPermissionsActive by remember {
                mutableStateOf(
                    prefsStorage.get().city!!.isNotEmpty() &&
                        AppPermissionsHelper.needsEntryPermissions(context),
                )
            }
            var pendingLocationNavigation by remember { mutableStateOf(false) }

            fun navigateToLocationScreen() {
                if (checkLocationPermission()) {
                    launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                } else {
                    navController.navigate(route = Screens.Location)
                }
            }

            EntryPermissionsFlow(
                activity = this@MainApplicationActivity,
                active = entryPermissionsActive,
                onFinished = {
                    entryPermissionsActive = false
                    if (pendingLocationNavigation) {
                        pendingLocationNavigation = false
                        navigateToLocationScreen()
                    }
                },
            )
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
                    val launchCount = sharedPreferencesRepository.incrementLaunchCount()
                    val shouldShowEntryAds = launchCount >= 3
                    val shouldShowNativeAd = BuildConfig.showNativeAd.toBoolean()

                    if (checkInternetConnection(context = context)) {
                        YandexAds.initialize(context) {
                            logd(message = "RSY initialized!")
                        }

                        if (shouldShowEntryAds || shouldShowNativeAd) {
                            isAdsLoading.value = true
                        } else {
                            isAdsLoading.value = false
                        }

                        if (shouldShowEntryAds) {
//                    region::Open app ads
                            val appOpenAdLoadListener = object : AppOpenAdLoadListener {
                                override fun onAdLoaded(appOpenAd: AppOpenAd) {
                                    // The ad was loaded successfully. Now you can show loaded ad.
                                    myAppOpenAd = appOpenAd
                                    myAppOpenAd?.show(this@MainApplicationActivity)
                                    isAdsLoading.value = false
                                }

                                override fun onAdFailedToLoad(adRequestError: AdRequestError) {
                                    isAdsLoading.value = false
                                    // Ad failed to load with AdRequestError.
                                    // Attempting to load a new ad from the onAdFailedToLoad() method is strongly discouraged.
                                }
                            }

                            myAppOpenAd?.setAdEventListener(appOpenAdEventListener)
                            appOpenAdLoader.loadAd(openAdsRequest, appOpenAdLoadListener)
//                    endregion
                        }

//                    region::Native ads
                        if (shouldShowNativeAd) {
                            val nativeAdsLoader = NativeBulkAdLoader(context)
                            val nativeAdsRequest = AdRequest.Builder(BuildConfig.NATIVE_ADS_ID).build()
                            nativeAdsLoader.loadAds(
                                nativeAdsRequest,
                                1,
                                object : NativeBulkAdLoadListener {
                                    override fun onAdsFailedToLoad(p0: AdRequestError) {
                                        logd(p0.toString())
                                        adsError.value = true
                                        isAdsLoading.value = false
                                    }

                                    override fun onAdsLoaded(nativeAds: List<NativeAd>) {
                                        list.value = nativeAds.toMutableList()
                                        adsError.value = false
                                        isAdsLoading.value = false
                                    }
                                }
                            )
                        }
//                    endregion
                    }
                }
            }
//            endregion

            val contentViewModel = remember {
                val mapGridHttpClient = HttpClient(CIO) {
                    install(HttpTimeout) {
                        requestTimeoutMillis = 5000
                        connectTimeoutMillis = 5000
                        socketTimeoutMillis = 5000
                    }
                }
                ContentViewModel(
                    storeFactory = DefaultStoreFactory(),
                    sharedPreferencesStorage = sharedPreferencesStorage,
                    weatherSession = weatherSession,
                    mapGridWeatherRepository = MapGridWeatherRepository(httpClient = mapGridHttpClient),
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
                    },
                )
            }

            LaunchedEffect(list.value, isAdsLoading.value) {
                contentViewModel.updateAds(
                    adsList = list.value,
                    isAdsLoading = isAdsLoading.value,
                )
            }

            LaunchedEffect(isDarkTheme.value) {
                contentViewModel.updateTheme(isDarkTheme.value)
            }

            WeatherAppTheme(darkTheme = isDarkTheme.value) {
                SetupUIController(darkTheme = isDarkTheme.value)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    NavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        startDestination =
                        if (prefsStorage.get().city!!.isEmpty()) {
                            Screens.Authorization
                        } else {
                            Screens.Content
                        }
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

                                            is MainViewModel.Output.DoHttpResponse -> {
//                                                httpHolder.value = 1
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
                                            AuthorizationViewModel.Output.OpenLocationScreen -> {
                                                if (AppPermissionsHelper.needsEntryPermissions(context)) {
                                                    pendingLocationNavigation = true
                                                    entryPermissionsActive = true
                                                } else {
                                                    navigateToLocationScreen()
                                                }
                                            }
                                        }
                                    }
                                )
                            )
                        }
                        composable(route = Screens.Location) {
                            LocationScreen(
                                locationViewModel = LocationViewModel(
                                    storeFactory = DefaultStoreFactory(),
                                    locationRepository = LocationRepositoryImpl(context = context),
                                    output = { output ->
                                        when (output) {
                                            LocationViewModel.Output.BackStackClicked -> {
                                                navController.popBackStack()
                                            }

                                            is LocationViewModel.Output.OpenContentScreen -> {
                                                weatherSession.markCityChanged()
                                                navController.navigate(route = Screens.Content) {
                                                    popUpTo(route = Screens.Authorization) {
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
                            val contentRefreshToken by weatherSession.contentRefreshToken.collectAsState()
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route

                            LaunchedEffect(currentRoute, contentRefreshToken) {
                                if (currentRoute == Screens.Content &&
                                    weatherSession.shouldFetchOnContentEnter()
                                ) {
                                    val loaded = loadWeatherIntoSession()
                                    if (!loaded) {
                                        navController.navigate(route = Screens.NoConnection)
                                    }
                                }
                            }

                            ContentScreen(contentViewModel = contentViewModel)
                        }
                        composable(route = Screens.Settings) {
                            val settingsViewModel = remember {
                                SettingsViewModel(
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
                                                weatherSession.markSettingsChanged()
                                                coroutine.launch {
                                                    if (!loadWeatherIntoSession()) {
                                                        navController.navigate(route = Screens.NoConnection)
                                                    }
                                                }
                                            }

                                            SettingsViewModel.Output.RescheduleNotifications -> {
                                                WeatherNotificationScheduler.schedule(context = context)
                                            }

                                            is SettingsViewModel.Output.OpenSetting -> {
                                                navController.navigate(route = Screens.AboutApp)
                                            }

                                            SettingsViewModel.Output.LeaveFeedBack -> {
                                                val url = Constants.Links.leaveFeedback
                                                val intent = Intent(Intent.ACTION_VIEW)
                                                intent.data = Uri.parse(url)
                                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                                startActivity(intent)
                                            }

                                            SettingsViewModel.Output.Share -> {
                                                val type = "text/plain"
                                                val subject = sharedDesc
                                                val extraText = Constants.Links.share_link
                                                val shareWith = "ShareWith"

                                                val intent = Intent(Intent.ACTION_SEND)
                                                intent.type = type
                                                intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                                                intent.putExtra(Intent.EXTRA_TEXT, extraText)

                                                ContextCompat.startActivity(
                                                    context,
                                                    Intent.createChooser(intent, shareWith),
                                                    null
                                                )
                                            }

                                            SettingsViewModel.Output.OpenWidgetConfig -> {
                                                if (checkReadStoragePermission()) {
                                                    launcherReadExtStorage.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                                } else {
                                                    navController.navigate(route = Screens.WidgetConfig)
                                                }
                                            }
                                        }
                                    },
                                )
                            }
                            var pendingNotificationIndex by remember { mutableStateOf<Int?>(null) }
                            val requestNotificationSetupPermissions = rememberNotificationSetupPermissionHandler(
                                activity = this@MainApplicationActivity,
                            ) {
                                pendingNotificationIndex?.let { index ->
                                    settingsViewModel.onEvent(
                                        SettingsStore.Intent.ChangeNotificationItem(
                                            value = NotificationPeriods.itemForIndex(index),
                                        ),
                                    )
                                    pendingNotificationIndex = null
                                }
                            }

                            SettingsScreen(
                                settingsViewModel = settingsViewModel,
                                onNotificationIntervalSelect = { index ->
                                    pendingNotificationIndex = index
                                    requestNotificationSetupPermissions()
                                },
                            )
                        }
                        composable(route = Screens.AirQuality) {
                            val weatherState by weatherSession.state.collectAsState()
                            AirQualityScreen(
                                airQualityViewModel = AirQualityViewModel(
                                    storeFactory = DefaultStoreFactory(),
                                    airQualityData = weatherState.airQualityData,
                                    output = { output ->
                                        when (output) {
                                            AirQualityViewModel.Output.OpenContentScreen -> {
                                                navController.popBackStack()
                                            }
                                        }
                                    }
                                )
                            )
                        }
                        composable(route = Screens.AboutApp) {
                            AboutApp(aboutAppViewModel = AboutAppViewModel(
                                storeFactory = DefaultStoreFactory(),
                                appName = stringResource(id = R.string.app_name),
                                output = { output ->
                                    when (output) {
                                        AboutAppViewModel.Output.OpenSettingsScreen -> {
                                            navController.popBackStack()
                                        }
                                    }
                                }
                            ))
                        }
                        composable(route = Screens.NoConnection) {
                            NoConnection(noConnectionViewModel = NoConnectionViewModel(
                                storeFactory = DefaultStoreFactory(),
                                output = { output ->
                                    when (output) {
                                        NoConnectionViewModel.Output.TryInternetConnection -> {
                                            coroutine.launch {
                                                if (loadWeatherIntoSession()) {
                                                    navController.popBackStack()
                                                }
                                            }
                                        }
                                    }
                                }
                            ))
                        }
                        composable(route = Screens.WidgetConfig) {
                            WidgetConfig(
                                widgetConfigViewModel = WidgetConfigViewModel(
                                    storeFactory = DefaultStoreFactory(),
                                    sharedPreferencesStorage = sharedPreferencesStorage,
                                    output = { output ->
                                        when (output) {
                                            WidgetConfigViewModel.Output.BackStackClicked -> {
                                                navController.popBackStack()
                                            }
                                        }
                                    }
                                )
                            )
                        }
                    }

                    if (showWeatherErrorDialog) {
                        AlertDialog(
                            onDismissRequest = { showWeatherErrorDialog = false },
                            title = {
                                Text(text = stringResource(id = R.string.error_code))
                            },
                            text = {
                                Text(text = stringResource(id = R.string.error_occurred))
                            },
                            confirmButton = {
                                TextButton(onClick = { showWeatherErrorDialog = false }) {
                                    Text(text = stringResource(id = R.string.error_ok))
                                }
                            },
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        WeatherNotificationScheduler.schedule(context = this)
    }

    private fun checkReadStoragePermission(): Boolean = ContextCompat.checkSelfPermission(
        App.INSTANCE.applicationContext,
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) != PackageManager.PERMISSION_GRANTED

    override fun onBackPressed() {
        when (navController.currentDestination!!.route) {
            Screens.Content -> {
                finish()
            }

            else -> {
                navController.popBackStack()
            }
        }
    }
//    endregion

    //    region::Private function
    private fun checkLocationPermission(): Boolean = ContextCompat.checkSelfPermission(
        App.INSTANCE.applicationContext,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED

    private inline fun <reified T> convertStringToJson(jsonString: String?, default: T): T {
        if (jsonString.isNullOrBlank()) return default
        return runCatching { Gson().fromJson(jsonString, T::class.java) }
            .onFailure { logd("JSON parse error: ${it.message}") }
            .getOrDefault(default)
    }

    private fun HttpStatusCode.isSuccessful(): Boolean = value in 200..299

    private suspend fun fetchAirQuality(
        airQualityRepository: AirQualityRepositoryImpl,
        owmResponse: OwmResponse,
        weatherApiResponse: WeatherApiResponse,
    ): AirQualityData {
        val lat = owmResponse.coord?.lat ?: weatherApiResponse.location?.lat ?: return AirQualityData()
        val lon = owmResponse.coord?.lon ?: weatherApiResponse.location?.lon ?: return AirQualityData()
        return runCatching { airQualityRepository.getAirQuality(lat, lon) }
            .getOrDefault(AirQualityData())
    }

    private fun OwmResponse.hasCurrentWeather(): Boolean =
        base.orEmpty().isNotEmpty() || main?.temp != null

    private data class WeatherFetchResult(
        val owmCode: HttpStatusCode,
        val owmHourlyCode: HttpStatusCode,
        val weatherApiForecastCode: HttpStatusCode,
        val weatherApiCode: Int,
        val owmResponse: OwmResponse,
        val owmHourlyResponse: MainWeatherResponse,
        val weatherApiResponse: WeatherApiResponse,
        val astronomyAstro: Astro?,
        val useOwmForCurrent: Boolean,
    )

    private suspend fun fetchWeatherData(
        httpClientStorage: HttpClientStorage,
        weatherSession: WeatherSession,
    ): WeatherFetchResult {
        val forecastEither = httpClientStorage.getWeatherApiForecast<String, HttpStatusCode, GMTDate>()
        val forecastCode = forecastEither.httpStatusCode
        val forecastResponse = if (forecastCode.isSuccessful()) {
            convertStringToJson(
                jsonString = forecastEither.data,
                default = WeatherApiResponse(),
            )
        } else {
            logd("WeatherAPI forecast request failed: ${forecastCode.value}")
            WeatherApiResponse()
        }

        var weatherApiCurrentCode = -1
        var resolvedCurrent = resolveWeatherApiCurrent(forecastResponse)
        var location = forecastResponse.location
        val weatherApiHasForecast = forecastResponse.forecast?.forecastday.orEmpty().isNotEmpty()
        val weatherApiHasHourly = weatherApiForecastHasHourlyData(forecastResponse.forecast)
        val weatherApiDayCount = weatherApiForecastDayCount(forecastResponse.forecast)

        if (resolvedCurrent == null) {
            val currentEither = httpClientStorage.getWeatherApiCurrent<String, HttpStatusCode, GMTDate>()
            weatherApiCurrentCode = currentEither.httpStatusCode.value
            if (currentEither.httpStatusCode.isSuccessful()) {
                val weatherApiCurrentResponse = convertStringToJson(
                    jsonString = currentEither.data,
                    default = WeatherApiResponse(),
                )
                resolvedCurrent = weatherApiCurrentResponse.current?.takeIf {
                    it.condition?.text.orEmpty().isNotEmpty()
                }
                location = weatherApiCurrentResponse.location ?: location
            } else {
                logd("WeatherAPI current request failed: $weatherApiCurrentCode")
            }
        }

        val weatherApiHasCurrent = resolvedCurrent != null
        if (weatherApiHasCurrent || weatherApiHasForecast) {
            weatherSession.setLoading(false)
        }

        var owmCode = HttpStatusCode(0, "Network error")
        var owmResponse = OwmResponse()
        var owmHourlyCode = HttpStatusCode(-1, "Not fetched")
        var owmHourlyResponse = MainWeatherResponse()

        val needOwmCurrent = !weatherApiHasCurrent
        val needOwmHourly = !weatherApiHasForecast || !weatherApiHasHourly || weatherApiDayCount < 7
        if (weatherApiDayCount in 1..6) {
            logd("WeatherAPI returned $weatherApiDayCount days (requested 7), will supplement from OWM")
        }
        if (needOwmCurrent || needOwmHourly) {
            if (needOwmCurrent) {
                val weatherEither = httpClientStorage.get<String, HttpStatusCode, GMTDate>()
                owmCode = weatherEither.httpStatusCode
                owmResponse = if (owmCode.isSuccessful()) {
                    convertStringToJson(jsonString = weatherEither.data, default = OwmResponse())
                } else {
                    logd("OWM request failed: ${owmCode.value}")
                    OwmResponse()
                }
            }

            if (needOwmHourly) {
                val hourlyEither = httpClientStorage.getHourly<String, HttpStatusCode, GMTDate>()
                owmHourlyCode = hourlyEither.httpStatusCode
                owmHourlyResponse = if (owmHourlyCode.isSuccessful()) {
                    convertStringToJson(
                        jsonString = hourlyEither.data,
                        default = MainWeatherResponse(),
                    )
                } else {
                    logd("OWM hourly request failed: ${owmHourlyCode.value}")
                    MainWeatherResponse()
                }
            }
        }

        val owmCurrentSuccess = owmCode.isSuccessful() && owmResponse.hasCurrentWeather()
        val useOwmForCurrent = needOwmCurrent && owmCurrentSuccess
        if (useOwmForCurrent || (!weatherApiHasCurrent && !weatherApiHasForecast)) {
            weatherSession.setLoading(false)
        }

        val astronomyDate = forecastResponse.forecast?.forecastday?.firstOrNull()?.date
            ?: LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        val astronomyEither = httpClientStorage.getWeatherApiAstronomy<String, HttpStatusCode, GMTDate>(
            date = astronomyDate,
        )
        val astronomyResponse = if (astronomyEither.httpStatusCode.isSuccessful()) {
            convertStringToJson(
                jsonString = astronomyEither.data,
                default = WeatherApiAstronomyResponse(),
            )
        } else {
            logd("WeatherAPI astronomy request failed: ${astronomyEither.httpStatusCode.value}")
            WeatherApiAstronomyResponse()
        }
        val astronomyAstro = astronomyResponse.astronomy?.astro?.takeIf {
            it.moonrise.orEmpty().isNotBlank() && it.moonrise != "0"
        } ?: forecastResponse.forecast?.forecastday?.firstOrNull()?.astro

        val weatherApiResponse = WeatherApiResponse(
            location = location,
            current = resolvedCurrent,
            forecast = forecastResponse.forecast,
            alerts = forecastResponse.alerts,
        )

        val weatherApiCode = when {
            useOwmForCurrent -> -1
            weatherApiCurrentCode > 0 && resolvedCurrent == null -> weatherApiCurrentCode
            weatherApiResponse.hasCurrentWeather() -> -1
            !forecastCode.isSuccessful() -> forecastCode.value
            else -> -1
        }

        return WeatherFetchResult(
            owmCode = owmCode,
            owmHourlyCode = owmHourlyCode,
            weatherApiForecastCode = forecastCode,
            weatherApiCode = weatherApiCode,
            owmResponse = owmResponse,
            owmHourlyResponse = owmHourlyResponse,
            weatherApiResponse = weatherApiResponse,
            astronomyAstro = astronomyAstro,
            useOwmForCurrent = useOwmForCurrent,
        )
    }


    private fun checkInternetConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.activeNetwork
        } else {
            null
        }
        if (network != null) {
            val capabilities = connectivityManager.getNetworkCapabilities(network)

            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                }
            } else {
                return false
            }
        } else {
            return true
        }
        return true
    }

    private fun clearAppOpenAd() {
        myAppOpenAd?.setAdEventListener(null)
//        appOpenAd = null
    }
//    endregion

    private inner class AdEventListener : AppOpenAdEventListener {
        override fun onAdShown() {
            // Called when ad is shown.
        }

        override fun onAdFailedToShow(adError: AdError) {
            // Called when ad failed to show.
        }

        override fun onAdDismissed() {
            // Called when ad is dismissed.
            // Clean resources after dismiss and preload new ad.
            clearAppOpenAd()
        }

        override fun onAdClicked() {
            // Called when a click is recorded for an ad.
        }

        override fun onAdImpression(impressionData: ImpressionData?) {
            // Called when an impression is recorded for an ad.
            // Get Impression Level Revenue Data in argument.
        }
    }

//    region::Future releases
//private fun addShortcutApiAfter26 (context: Context) {
//    if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
//        val shortcutInfo = ShortcutInfoCompat.Builder(context, "shortcut1")
//            .setShortLabel("Shortcut 1")
//            .setIcon(IconCompat.createWithResource(context, R.drawable.weather_logo))
//            .setIntent(Intent(context, MainApplicationActivity::class.java))
//            .build()
//
//        val pinnedShortcutCallbackIntent =
//            ShortcutManagerCompat.createShortcutResultIntent(context, shortcutInfo)
//
//        val successCallback = PendingIntent.getBroadcast(
//            context,
//            0,
//            pinnedShortcutCallbackIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//        ShortcutManagerCompat.requestPinShortcut(context, shortcutInfo, successCallback.intentSender)
//    }
//}
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun addShortcutApiBefore26(context: Context){
//        val shortcutManager = getSystemService(ShortcutManager::class.java)
//
//        if (shortcutManager.isRequestPinShortcutSupported()) {
//            val shortcutInfo = ShortcutInfo.Builder(context, "shortcut1")
//                .setShortLabel("Shortcut 1")
//                .setIcon(Icon.createWithResource(context, R.drawable.weather_logo))
//                .setIntent(Intent(context, MainApplicationActivity::class.java))
//                .build()
//
//            val successCallback = PendingIntent.getBroadcast(
//                context,
//                0,
//                Intent(context, ShortcutReceiver::class.java),
//                PendingIntent.FLAG_UPDATE_CURRENT
//            )
//
//            shortcutManager.requestPinShortcut(shortcutInfo, successCallback.intentSender)
//        }
//    }
//    endregion
}