package ru.plumsoftware.weatherforecastru.presentation.content.presentation

import android.annotation.SuppressLint
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.yandex.mobile.ads.common.AdBindingResult
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.nativeads.MediaView
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdEventListener
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener
import com.yandex.mobile.ads.nativeads.NativeAdLoader
import com.yandex.mobile.ads.nativeads.NativeAdView
import com.yandex.mobile.ads.nativeads.NativeAdViewBinder
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.AdRatingView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.application.App
import ru.plumsoftware.weatherforecastru.data.utilities.logd
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.HourlyForecastCard
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.HttpErrorComponent
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.MoonAstronomyCard
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.SunriseSunsetCard
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.WeatherDetailsGrid
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.WeatherAlertsSection
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.WeatherHero
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.mapAlerts
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.WeatherCollapsingTopBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.DailyForecastCard
import ru.plumsoftware.weatherforecastru.data.map.WeatherGridLabel
import ru.plumsoftware.weatherforecastru.data.map.WeatherMapLayer
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.PrecipitationMapCard
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.PrecipitationMapFullScreen
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.mergeDailyForecastItems
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.mapOwmDailyItems
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.mapWeatherApiDailyItems
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.mapOwmHourlyItems
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.mapWeatherApiHourlyItems
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.weatherApiReferenceDate
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.mapWeatherApiHourlyItems
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.resolveSunriseSunsetTimes
import ru.plumsoftware.weatherforecastru.data.weather.WeatherIconCodes
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.weatherApiTemperature
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.weatherApiWindSpeed
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.weatherDescriptionResForOwmId
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.weatherDescriptionResForWeatherApiCode
import ru.plumsoftware.weatherforecastru.presentation.content.presentation.components.hasCurrentWeather
import ru.plumsoftware.weatherforecastru.presentation.ui.Dimens
import ru.plumsoftware.weatherforecastru.presentation.ui.NavigationBarSpacer
import ru.plumsoftware.weatherforecastru.presentation.content.store.ContentStore
import ru.plumsoftware.weatherforecastru.presentation.content.viewmodel.ContentViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ContentScreen(contentViewModel: ContentViewModel) {

//    region::States
    val state by contentViewModel.state.collectAsState()
    val coroutine = rememberCoroutineScope()
//    endregion

//    region::Labels
    LaunchedEffect(contentViewModel) {
        contentViewModel.label.collect { label ->
            when (label) {
                is ContentStore.Label.OpenLocation -> {
                    contentViewModel.onOutput(ContentViewModel.Output.OpenLocationScreen)
                }

                ContentStore.Label.OpenSettings -> {
                    contentViewModel.onOutput(ContentViewModel.Output.OpenSettingsScreen)
                }

                is ContentStore.Label.ChangeHourly -> {

                }

                is ContentStore.Label.OpenAirQuality -> {
                    contentViewModel.onOutput(ContentViewModel.Output.OpenAirQualityScreen)
                }
            }
        }
    }
//    endregion

    ContentScreen(
        state = state,
        contentViewModel = contentViewModel,
        coroutine = coroutine
    )
}

@Composable
private fun ContentScreen(
    state: ContentStore.State,
    contentViewModel: ContentViewModel,
    coroutine: CoroutineScope
) {
    var isPrecipitationMapExpanded by remember { mutableStateOf(false) }
    var mapGridLabels by remember { mutableStateOf<List<WeatherGridLabel>>(emptyList()) }
    val fallbackLatitude = state.weatherApiResponse.location?.lat
        ?: state.owmHourlyResponse.city.coord.lat
    val fallbackLongitude = state.weatherApiResponse.location?.lon
        ?: state.owmHourlyResponse.city.coord.lon
    var mapLatitude by remember { mutableStateOf(fallbackLatitude) }
    var mapLongitude by remember { mutableStateOf(fallbackLongitude) }

    LaunchedEffect(fallbackLatitude, fallbackLongitude, isPrecipitationMapExpanded) {
        val (latitude, longitude) = contentViewModel.resolveDeviceMapCoordinates(
            fallbackLatitude = fallbackLatitude,
            fallbackLongitude = fallbackLongitude,
        )
        mapLatitude = latitude
        mapLongitude = longitude
    }

    LaunchedEffect(
        mapLatitude,
        mapLongitude,
        state.weatherMapLayer,
        state.weatherUnits.unitsValue,
        state.windSpeed.windPresentation,
        isPrecipitationMapExpanded,
    ) {
        val labelsLayer = if (isPrecipitationMapExpanded) {
            state.weatherMapLayer
        } else {
            WeatherMapLayer.Temperature
        }
        mapGridLabels = contentViewModel.loadMapGridLabels(
            latitude = mapLatitude,
            longitude = mapLongitude,
            mapLayer = labelsLayer,
            unitsValue = state.weatherUnits.unitsValue,
            windUnitLabel = state.windSpeed.windPresentation,
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        val owm = state.owmResponse
        val weatherApi = state.weatherApiResponse
            val today = weatherApi.forecast?.forecastday?.firstOrNull()
            val apiTemperatures = weatherApiTemperature(
                current = weatherApi.current,
                day = today?.day,
                unitsValue = state.weatherUnits.unitsValue,
            )
            val (sunriseTime, sunsetTime) = resolveSunriseSunsetTimes(
                useOwmForCurrent = state.useOwmForCurrent,
                owmSunriseEpochSec = owm.sys?.sunrise,
                owmSunsetEpochSec = owm.sys?.sunset,
                forecastAstro = today?.astro,
                astronomyAstro = state.astronomyAstro,
            )
            val hourlyEntries = mapWeatherApiHourlyItems(
                forecast = weatherApi.forecast,
                unitsValue = state.weatherUnits.unitsValue,
                referenceLocalTime = weatherApi.location?.localtime,
            ).ifEmpty {
                mapOwmHourlyItems(
                    hourlyResponse = state.owmHourlyResponse,
                    unitsValue = state.weatherUnits.unitsValue,
                    owmHourlyCode = state.owmHourlyCode,
                )
            }
            val dailyItems = mergeDailyForecastItems(
                primary = mapWeatherApiDailyItems(
                    forecast = weatherApi.forecast,
                    unitsValue = state.weatherUnits.unitsValue,
                    referenceLocalTime = weatherApi.location?.localtime,
                ),
                fallback = mapOwmDailyItems(
                    hourlyResponse = state.owmHourlyResponse,
                    unitsValue = state.weatherUnits.unitsValue,
                    owmHourlyCode = state.owmHourlyCode,
                ),
            )
            val todayDate = weatherApiReferenceDate(weatherApi.location?.localtime)
            val weatherAlerts = remember(weatherApi.alerts) {
                mapAlerts(weatherApi.alerts)
            }
            val feelsLike: Int? = when {
                state.useOwmForCurrent &&
                    state.owmCode !in 300..599 &&
                    owm.base.orEmpty().isNotEmpty() -> owm.main?.feelsLike?.toInt()
                !state.useOwmForCurrent &&
                    state.weatherApiCode !in 300..599 &&
                    weatherApi.hasCurrentWeather() -> apiTemperatures.feelsLike
                else -> null
            }
            val listState = rememberLazyListState()
            val density = LocalDensity.current
            val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
            val collapseScrollThresholdPx = with(density) { 56.dp.toPx() }
            val collapsed by remember {
                derivedStateOf {
                    listState.firstVisibleItemIndex > 0 ||
                        listState.firstVisibleItemScrollOffset > collapseScrollThresholdPx
                }
            }
            val topBarContentPadding = statusBarPadding + if (collapsed) {
                Dimens.collapsingTopBarCollapsedHeight
            } else {
                Dimens.collapsingTopBarExpandedHeight
            }

            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(Dimens.sectionGap, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(top = topBarContentPadding),
                modifier = Modifier.fillMaxSize(),
                content = {
                item {
                    if (state.useOwmForCurrent) {
                        if (state.owmCode in 300..599) {
                            HttpErrorComponent(state.owmCode)
                        } else if (owm.base.orEmpty().isNotEmpty()) {
                            WeatherHero(
                                temperature = owm.main?.temp?.toInt() ?: 0,
                                description = stringResource(
                                    weatherDescriptionResForOwmId(
                                        owm.weather.firstOrNull()?.id ?: 800,
                                    ),
                                ),
                                feelsLike = owm.main?.feelsLike?.toInt() ?: 0,
                                high = owm.main?.tempMax?.toInt() ?: 0,
                                low = owm.main?.tempMin?.toInt() ?: 0,
                                weatherIconCode = WeatherIconCodes.fromOwmIcon(
                                    owm.weather.firstOrNull()?.icon,
                                ),
                            )
                        }
                    } else if (state.weatherApiCode in 300..599) {
                        HttpErrorComponent(state.weatherApiCode)
                    } else if (weatherApi.hasCurrentWeather()) {
                        WeatherHero(
                            temperature = apiTemperatures.current ?: 0,
                            description = stringResource(
                                weatherDescriptionResForWeatherApiCode(
                                    weatherApi.current?.condition?.code ?: 1000,
                                ),
                            ),
                            feelsLike = apiTemperatures.feelsLike ?: 0,
                            high = apiTemperatures.high ?: apiTemperatures.current ?: 0,
                            low = apiTemperatures.low ?: apiTemperatures.current ?: 0,
                            weatherIconCode = WeatherIconCodes.fromWeatherApiCode(
                                weatherApi.current?.condition?.code ?: 1000,
                                isDay = weatherApi.current?.isDay == 1,
                            ),
                        )
                    }
                }

                item {
                    if (state.weatherApiCode !in 300..599 && weatherAlerts.isNotEmpty()) {
                        WeatherAlertsSection(alerts = weatherAlerts)
                    }
                }

                item {
                    if (state.adsList.isNotEmpty() &&
                        (state.useOwmForCurrent && state.owmCode !in 300..599 ||
                            !state.useOwmForCurrent && state.weatherApiCode !in 300..599)
                    ) {
                        NativeAdBanner(
                            adsList = state.adsList,
                            coroutine = coroutine,
                        )
                    }
                }

                item {
                    if (hourlyEntries.isNotEmpty()) {
                        HourlyForecastCard(entries = hourlyEntries)
                    }
                }

                item {
                    if (dailyItems.isNotEmpty()) {
                        DailyForecastCard(
                            items = dailyItems,
                            today = todayDate,
                        )
                    }
                }

                item {
                    PrecipitationMapCard(
                        latitude = mapLatitude,
                        longitude = mapLongitude,
                        gridLabels = mapGridLabels,
                        onExpand = { isPrecipitationMapExpanded = true },
                    )
                }

                item {
                    if (state.useOwmForCurrent && state.owmCode !in 300..599) {
                        WeatherDetailsGrid(
                            humidity = owm.main?.humidity ?: 0,
                            windSpeed = "${owm.wind?.speed?.toInt() ?: 0} ${state.windSpeed.windPresentation}",
                            windDirection = windDirectionFull(owm.wind?.deg ?: 0),
                            visibilityKm = stringResource(
                                R.string.visibility_km,
                                (owm.visibility ?: 0) / 1000,
                            ),
                            aqi = state.airQualityData.aqi,
                            aqiLabel = state.airQualityData.aqiLabel,
                            onAirQualityClick = {
                                contentViewModel.onEvent(ContentStore.Intent.OpenAirQuality)
                            },
                        )
                    } else if (!state.useOwmForCurrent && state.weatherApiCode !in 300..599) {
                        WeatherDetailsGrid(
                            humidity = weatherApi.current?.humidity ?: 0,
                            windSpeed = "${weatherApiWindSpeed(weatherApi.current, state.weatherUnits.unitsValue)} ${state.windSpeed.windPresentation}",
                            windDirection = windDirectionFull(weatherApi.current?.windDegree ?: 0),
                            visibilityKm = stringResource(
                                R.string.visibility_km,
                                weatherApi.current?.visKm?.toInt() ?: 0,
                            ),
                            aqi = state.airQualityData.aqi,
                            aqiLabel = state.airQualityData.aqiLabel,
                            onAirQualityClick = {
                                contentViewModel.onEvent(ContentStore.Intent.OpenAirQuality)
                            },
                        )
                    }
                }

                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(Dimens.sectionGap, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (sunriseTime != null && sunsetTime != null) {
                            SunriseSunsetCard(
                                sunrise = sunriseTime,
                                sunset = sunsetTime,
                            )
                        }

                        state.astronomyAstro?.let { astro ->
                            MoonAstronomyCard(astro = astro)
                        }

                        if (state.useOwmForCurrent && state.owmCode in 300..599) {
                            HttpErrorComponent(
                                httpCode = state.owmCode,
                                modifier = Modifier.padding(horizontal = Dimens.screenPaddingH),
                            )
                        } else if (!state.useOwmForCurrent && state.weatherApiCode in 300..599) {
                            HttpErrorComponent(
                                httpCode = state.weatherApiCode,
                                modifier = Modifier.padding(horizontal = Dimens.screenPaddingH),
                            )
                        }
                    }
                }

                item { NavigationBarSpacer() }
            })

            WeatherCollapsingTopBar(
                city = state.city,
                country = state.country,
                feelsLike = feelsLike,
                collapsed = collapsed,
                onMenuClick = {
                    contentViewModel.onEvent(
                        ContentStore.Intent.DropDownMenuChange(value = state.dropDownState),
                    )
                },
                onOpenLocation = {
                    contentViewModel.onEvent(ContentStore.Intent.OpenLocation)
                },
                onOpenSettings = {
                    contentViewModel.onEvent(ContentStore.Intent.OpenSettings)
                },
                onOpenAirQuality = {
                    contentViewModel.onEvent(ContentStore.Intent.OpenAirQuality)
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(1f),
            )

        PrecipitationMapFullScreen(
            visible = isPrecipitationMapExpanded,
            latitude = mapLatitude,
            longitude = mapLongitude,
            mapLayer = state.weatherMapLayer,
            gridLabels = mapGridLabels,
            city = state.city,
            country = state.country,
            onMapLayerChange = { layer ->
                contentViewModel.onEvent(ContentStore.Intent.ChangeWeatherMapLayer(layer))
            },
            onBack = { isPrecipitationMapExpanded = false },
            modifier = Modifier
                .fillMaxSize()
                .zIndex(10f),
        )

        if (state.isWeatherLoading) {
            WeatherLoadingDialog()
        }
    }
}

@Composable
private fun WeatherLoadingDialog() {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(text = stringResource(id = R.string.loading_weather))
        },
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                CircularProgressIndicator(modifier = Modifier.size(32.dp))
                Text(text = stringResource(id = R.string.loading_weather_message))
            }
        },
        confirmButton = {},
    )
}

@Composable
private fun NativeAdBanner(
    adsList: List<NativeAd>,
    coroutine: CoroutineScope,
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.screenPaddingH),
        factory = { context ->
            val themedContext = ContextThemeWrapper(context, R.style.Theme_Погода)
            LayoutInflater.from(themedContext).inflate(R.layout.native_ads, null)
        },
        update = { view ->
            val mNativeAdView = view.findViewById<NativeAdView>(R.id.nativeAdView)
            val mediaView = view.findViewById<MediaView>(R.id.media)
            val age = view.findViewById<TextView>(R.id.age)
            val bodyView = view.findViewById<TextView>(R.id.tvAdvertiser)
            val callToAction = view.findViewById<TextView>(R.id.btnVisitSite)
            val domain = view.findViewById<TextView>(R.id.textViewDomain)
            val favicon = view.findViewById<ImageView>(R.id.adsPromo)
            val adsIcon = view.findViewById<ImageView>(R.id.adsIcon)
            val imageViewFeedback = view.findViewById<ImageView>(R.id.imageViewFeedback)
            val priceView = view.findViewById<TextView>(R.id.priceView)
            val storeView = view.findViewById<TextView>(R.id.storeView)
            val tvHeadline = view.findViewById<TextView>(R.id.tvHeadline)
            val rating = view.findViewById<AdRatingView>(R.id.rating)
            val warning = view.findViewById<TextView>(R.id.textViewWarning)

            coroutine.launch {
                for (nativeAd in adsList) {
                    showAd(
                        nativeAd,
                        mNativeAdView,
                        age,
                        bodyView,
                        callToAction,
                        domain,
                        favicon,
                        adsIcon,
                        imageViewFeedback,
                        mediaView,
                        priceView,
                        rating,
                        storeView,
                        tvHeadline,
                        warning,
                    )
                }
            }
        },
    )
}

//region::Functions
private fun calculateUVIndex(number: Int): String {
    return if (number > 0) {
        when (number) {
            in 0..2 -> "Низкий"
            in 3..5 -> "Умеренный"
            in 6..7 -> "Высокий"
            in 8..10 -> "Очень высокий"
            else -> "Опасный"
        }
    } else "0"
}

private fun windDirection(deg: Int): String {
    val directions = arrayOf(
        "С",
        "ССВ",
        "СВ",
        "ВСВ",
        "В",
        "ВЮВ",
        "ЮВ",
        "ЮЮВ",
        "Ю",
        "ЮЮЗ",
        "ЮЗ",
        "ЗЮЗ",
        "З",
        "ЗСЗ",
        "СЗ",
        "ССЗ"
    )
    val index = ((deg / 22.5) + 0.5).toInt() % 16
    return directions[index]
}

@Composable
private fun windDirectionFull(deg: Int): String {
    val directions = stringArrayResource(R.array.wind_directions_full)
    val index = ((deg / 22.5) + 0.5).toInt() % directions.size
    return directions[index]
}

private fun createNativeAdLoader(adUnitId: String): NativeAdLoader {
    return NativeAdLoader(App.INSTANCE.applicationContext)
}

private fun showAd(
    nativeAd: NativeAd,
    nativeAdView: NativeAdView,
    age: TextView,
    body: TextView,
    callToAction: TextView,
    domain: TextView,
    favicon: ImageView,
    icon: ImageView,
    feedback: ImageView,
    media: MediaView,
    price: TextView,
    rating: AdRatingView,
    sponsored: TextView,
    title: TextView,
    warning: TextView,
) {
    val nativeAdViewBinder = nativeAd.run {
        NativeAdViewBinder.Builder(nativeAdView)
            .setAgeView(age)
            .setBodyView(body)
            .setCallToActionView(callToAction)
            .setDomainView(domain)
            .setFaviconView(favicon)
            .setFeedbackView(feedback)
            .setIconView(icon)
            .setMediaView(media)
            .setPriceView(price)
            .setRatingView(rating)
            .setSponsoredView(sponsored)
            .setTitleView(title)
            .setWarningView(warning)
            .build()
    }

    when (val result = nativeAd.bindNativeAd(nativeAdViewBinder)) {
        is AdBindingResult.Failure -> {
            logd(result.exception.message.orEmpty())
        }
        AdBindingResult.Success -> {
            nativeAd.setNativeAdEventListener(NativeAdEventLogger())
        }
    }
}

private class NativeAdEventLogger : NativeAdEventListener {

    override fun onAdClicked() {
        // Called when a click is recorded for an ad.
    }

    override fun onImpression(data: ImpressionData?) {
        // Called when an impression is recorded for an ad.
    }
}

//endregion