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
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
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
import ru.plumsoftware.weatherforecast.BuildConfig
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.data.models.location.LocationItemDao
import ru.plumsoftware.weatherforecastru.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.weatherforecastru.data.utilities.logd
import ru.plumsoftware.weatherforecastru.domain.constants.Constants
import ru.plumsoftware.weatherforecastru.domain.remote.dto.either.WeatherEither
import ru.plumsoftware.weatherforecastru.domain.storage.HttpClientStorage
import ru.plumsoftware.weatherforecastru.domain.storage.LocationStorage
import ru.plumsoftware.weatherforecastru.domain.storage.SharedPreferencesStorage
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

class MainApplicationActivity : ComponentActivity(), KoinComponent {
    private var isDarkTheme = mutableStateOf(false)
    private lateinit var navController: NavHostController
    private lateinit var analytics: FirebaseAnalytics

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
            val sharedDesc = stringResource(id = R.string.share_description)

            analytics = Firebase.analytics
            isDarkTheme =
                remember { mutableStateOf(value = sharedPreferencesStorage.get().isDarkTheme) }
            navController = rememberNavController()
            val coroutine = rememberCoroutineScope()
            val OWM_VALUE = remember { mutableStateOf(OwmResponse()) }
            val WEATHER_API_VALUE = remember { mutableStateOf(WeatherApiResponse()) }
            val owmHttpCode = remember { mutableStateOf(-1) }
            val weatherApiHttpCode = remember { mutableStateOf(-1) }
            val httpHolder = remember { mutableStateOf(0) }
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
                        NativeAdRequestConfiguration.Builder("demo-native-content-yandex") //TODO(Replase)
                            .build()
                    nativeAdsLoader.loadAds(adRequestConfiguration, 1)
                }
            }

            when (httpHolder.value) {
                1 -> {
                    LaunchedEffect(true) {
                        coroutine.launch {
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
                        }
                    }
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

                                            is MainViewModel.Output.DoHttpResponse -> {
                                                httpHolder.value = 1
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
                                                        with(
                                                            doHttpResponse(
                                                                httpClientStorage = httpClientStorage,
                                                                launch = false
                                                            )
                                                        ) {
                                                            OWM_VALUE.value = second.first

                                                            owmHttpCode.value = first.first.value
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
                                                    with(
                                                        doHttpResponse(
                                                            httpClientStorage = httpClientStorage,
                                                            launch = false
                                                        )
                                                    ) {
                                                        OWM_VALUE.value = second.first

                                                        owmHttpCode.value = first.first.value
                                                    }
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
                                version = BuildConfig.VERSION_NAME,
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
                                                with(
                                                    doHttpResponse(
                                                        httpClientStorage = httpClientStorage,
                                                        launch = false
                                                    )
                                                ) {
                                                    OWM_VALUE.value = second.first

                                                    owmHttpCode.value = first.first.value
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
        scheduleBackgroundJob()
    }

    private fun checkReadStoragePermission(): Boolean = ContextCompat.checkSelfPermission(
        App.INSTANCE.applicationContext,
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) != PackageManager.PERMISSION_GRANTED

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
        val network = connectivityManager.activeNetwork
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
        return true
    }

    private fun scheduleBackgroundJob() {
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        val serviceName = ComponentName(this, MyJobService::class.java)
        val updatePeriod: Long = 21600000

        val jobInfo = JobInfo.Builder(JOB_ID, serviceName)
            .setPersisted(true) // Для сохранения задачи после перезагрузки устройства
            .setPeriodic(updatePeriod) // Обновление каждые 6 часов
            .build()

        jobScheduler.schedule(jobInfo)
    }
//    endregion
}