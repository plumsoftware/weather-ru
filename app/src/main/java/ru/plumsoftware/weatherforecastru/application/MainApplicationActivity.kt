package ru.plumsoftware.weatherforecastru.application

import android.Manifest
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.common.MobileAds
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration
import com.yandex.mobile.ads.nativeads.NativeBulkAdLoadListener
import com.yandex.mobile.ads.nativeads.NativeBulkAdLoader
import io.ktor.client.HttpClient
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
import ru.plumsoftware.weatherforecastru.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.weatherforecastru.data.utilities.logd
import ru.plumsoftware.weatherforecastru.data.remote.either.WeatherEither
import ru.plumsoftware.weatherforecastru.data.repository.LocationRepository
import ru.plumsoftware.weatherforecastru.data.repository.LocationRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.repository.OwmRepositoryImpl
import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepository
import ru.plumsoftware.weatherforecastru.data.repository.SharedPreferencesRepositoryImpl
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
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetOwmUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.weather.GetWeatherApiUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.widget.GetWidgetConfigUseCase
import ru.plumsoftware.weatherforecastru.data.usecase.widget.SaveWidgetConfigUseCase
import ru.plumsoftware.weatherforecastru.presentation.noconnection.presentation.NoConnection
import ru.plumsoftware.weatherforecastru.presentation.aboutapp.presentation.AboutApp
import ru.plumsoftware.weatherforecastru.presentation.aboutapp.viewmodel.AboutAppViewModel
import ru.plumsoftware.weatherforecastru.presentation.airquality.presentation.AirQualityScreen
import ru.plumsoftware.weatherforecastru.presentation.airquality.viewmodel.AirQualityViewModel
import ru.plumsoftware.weatherforecastru.presentation.authorization.viewmodel.AuthorizationViewModel
import ru.plumsoftware.weatherforecastru.presentation.authorization.presentation.AuthorizationScreen
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.ContentScreen
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
import ru.plumsoftware.weatherforecastru.service.JOB_ID
import ru.plumsoftware.weatherforecastru.service.MyJobService

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
            val client = HttpClient(CIO) {
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
                    requestTimeoutMillis = 30000
                }
            }

            val sharedPreferencesRepository = SharedPreferencesRepositoryImpl(context = context)

            val locationItemDao = room.dao
            val locationStorage = LocationStorage(
                getLastKnownLocationUseCase = GetLastKnownLocationUseCase(locationRepository = LocationRepositoryImpl(context = context))
            )
            sharedPreferencesStorage = SharedPreferencesStorage(
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
            val owmRepository = OwmRepositoryImpl(client = client, sharedPreferencesStorage = sharedPreferencesStorage)
            val weatherApiRepository = WeatherApiRepositoryImpl(client = client, sharedPreferencesStorage = sharedPreferencesStorage)
            val httpClientStorage = HttpClientStorage(
                getOwmUseCase = GetOwmUseCase(owmRepository = owmRepository),
                getWeatherApiUseCase = GetWeatherApiUseCase(weatherApiRepository = weatherApiRepository)
            )
            val context = LocalContext.current
            val sharedDesc = stringResource(id = R.string.share_description)
            val appOpenAdLoader: AppOpenAdLoader = AppOpenAdLoader(application)
            val adRequestConfigurationOpenAds =
                AdRequestConfiguration.Builder(BuildConfig.OPEN_ADS_ID).build()

            analytics = Firebase.analytics
            isDarkTheme =
                remember { mutableStateOf(value = sharedPreferencesStorage.get().isDarkTheme) }
            navController = rememberNavController()
            val coroutine = rememberCoroutineScope()
            val OWM_VALUE = remember { mutableStateOf(OwmResponse()) }
            val WEATHER_API_VALUE = remember { mutableStateOf(WeatherApiResponse()) }
            val owmHttpCode = remember { mutableStateOf(-1) }
            val weatherApiHttpCode = remember { mutableStateOf(-1) }
//            val httpHolder = remember { mutableStateOf(0) }
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

                    if (checkInternetConnection(context = context)) {
                        MobileAds.initialize(context) {
                            logd(message = "RSY initialized!")
                        }

                        isAdsLoading.value = true
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
                        appOpenAdLoader.setAdLoadListener(appOpenAdLoadListener)
                        appOpenAdLoader.loadAd(adRequestConfigurationOpenAds)
//                    endregion
//                    region::Native ads
                        if (BuildConfig.showNativeAd.toBoolean()) {
                            val nativeAdsLoader = NativeBulkAdLoader(context).apply {
                                setNativeBulkAdLoadListener(object : NativeBulkAdLoadListener {
//                                    override fun onAdsLoaded(p0: MutableList<NativeAd>) {
//                                        list.value = p0
//                                        adsError.value = false
//                                    }
//                                    TODO(Slava Deych): Remove later

                                    override fun onAdsFailedToLoad(p0: AdRequestError) {
                                        logd(p0.toString())
                                        adsError.value = true
                                        isAdsLoading.value = false
                                    }

                                    override fun onAdsLoaded(nativeAds: List<NativeAd>) {
                                        list.value = nativeAds.toMutableList()
                                        adsError.value = false
                                    }
                                })
                            }
                            val adRequestConfigurationNativeAds =
                                NativeAdRequestConfiguration.Builder(BuildConfig.NATIVE_ADS_ID)
                                    .build()
                            nativeAdsLoader.loadAds(adRequestConfigurationNativeAds, 1)
                        }
//                    endregion
                    }
                }
            }
//            endregion

            WeatherAppTheme(darkTheme = isDarkTheme.value) {
                SetupUIController()
                Surface {
                    NavHost(
                        navController = navController,
                        startDestination =
                        if (sharedPreferencesStorage.get().city!!.isEmpty()) {
                            Screens.Authorization
                        } else {
                            coroutine.launch {
                                if (checkInternetConnection(context)) {
                                    with(
                                        doHttpResponse(
                                            httpClientStorage = httpClientStorage,
                                            launch = true
                                        )
                                    ) {
                                        OWM_VALUE.value = second.first
                                        WEATHER_API_VALUE.value = second.second

                                        owmHttpCode.value = first.first.value
                                        weatherApiHttpCode.value = first.second.value
                                    }
                                } else {
                                    navController.navigate(route = Screens.NoConnection)
                                }
                            }
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
                                                        if (checkInternetConnection(context)) {
                                                            with(
                                                                doHttpResponse(
                                                                    httpClientStorage = httpClientStorage,
                                                                    launch = false
                                                                )
                                                            ) {
                                                                OWM_VALUE.value = second.first
                                                                WEATHER_API_VALUE.value =
                                                                    second.second

                                                                owmHttpCode.value =
                                                                    first.first.value
                                                                weatherApiHttpCode.value =
                                                                    first.second.value
                                                            }
                                                        } else {
                                                            navController.navigate(route = Screens.NoConnection)
                                                        }
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
                                    isDark = isSystemInDarkTheme(),
                                    owmCode = owmHttpCode.value,
                                    weatherApiCode = weatherApiHttpCode.value,
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
                                                    if (checkInternetConnection(context)) {
                                                        with(
                                                            doHttpResponse(
                                                                httpClientStorage = httpClientStorage,
                                                                launch = false
                                                            )
                                                        ) {
                                                            OWM_VALUE.value = second.first

                                                            owmHttpCode.value = first.first.value
                                                        }
                                                    } else {navController.navigate(route=Screens.NoConnection)}
                                                }
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
                                    }
                                )
                            )
                        }
                        composable(route = Screens.AirQuality) {
                            AirQualityScreen(
                                airQualityViewModel = AirQualityViewModel(
                                    storeFactory = DefaultStoreFactory(),
                                    airQuality = WEATHER_API_VALUE.value.current!!.airQuality!!,
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
                                            CoroutineScope(Dispatchers.IO).launch {
                                                if (checkInternetConnection(context)) {
                                                    with(
                                                        doHttpResponse(
                                                            httpClientStorage = httpClientStorage,
                                                            launch = false
                                                        )
                                                    ) {
                                                        OWM_VALUE.value = second.first

                                                        owmHttpCode.value = first.first.value
                                                    }
                                                } else {
                                                    navController.navigate(route = Screens.NoConnection)
                                                }
                                                CoroutineScope(Dispatchers.Main).launch {
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
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            scheduleBackgroundJob(sharedPreferencesStorage = sharedPreferencesStorage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    private inline fun <reified T> convertStringToJson(jsonString: String): T =
        Gson().fromJson(jsonString, T::class.java)

    private suspend fun doHttpResponse(
        httpClientStorage: HttpClientStorage,
        launch: Boolean
    ): Pair<Pair<HttpStatusCode, HttpStatusCode>, Pair<OwmResponse, WeatherApiResponse>> {

        val checkInternetConnection =
            checkInternetConnection(context = App.INSTANCE.applicationContext)

        if (checkInternetConnection) {
            if (launch)
                CoroutineScope(Dispatchers.Main).launch {
                    navController.navigate(route = Screens.Content)
                }
            var weatherEither: WeatherEither<String, HttpStatusCode, GMTDate> =
                httpClientStorage.get()
            val owmResponse = convertStringToJson<OwmResponse>(jsonString = weatherEither.data)
            val fistCode = weatherEither.httpStatusCode

            weatherEither = httpClientStorage.getWeatherApi()
            val weatherApiResponse =
                convertStringToJson<WeatherApiResponse>(jsonString = weatherEither.data)
            val secondCode = weatherEither.httpStatusCode

            return Pair(
                first = Pair(first = fistCode, second = secondCode),
                second = Pair(first = owmResponse, second = weatherApiResponse)
            )
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                navController.navigate(route = Screens.NoConnection)
            }
            return Pair(
                first = Pair(first = HttpStatusCode(-1, ""), second = HttpStatusCode(-1, "")),
                second = Pair(first = OwmResponse(), second = WeatherApiResponse())
            )
        }
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

    private fun scheduleBackgroundJob(sharedPreferencesStorage: ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage) {
        if (sharedPreferencesStorage.getNotificationItem().period > 0) {
            logd(sharedPreferencesStorage.getNotificationItem().period.toString())
            val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val pendingJob = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                jobScheduler.getPendingJob(JOB_ID)
            } else {
                null
            }

            val serviceName = ComponentName(this, MyJobService::class.java)

            val updatePeriod: Long = sharedPreferencesStorage.getNotificationItem().period

            val jobInfo =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    JobInfo.Builder(JOB_ID, serviceName)
                        .setPersisted(true)
                        .setExpedited(true)
                        .setPeriodic(updatePeriod)
                        .build()
                } else {
                    JobInfo.Builder(JOB_ID, serviceName)
                        .setPersisted(true)
                        .setPeriodic(updatePeriod)
                        .build()
                }

            if (pendingJob != null) {
                // Сервис уже запущен
                jobScheduler.cancel(JOB_ID)
            } else {
                // Сервис еще не запущен
            }

            jobScheduler.schedule(jobInfo)
        }
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