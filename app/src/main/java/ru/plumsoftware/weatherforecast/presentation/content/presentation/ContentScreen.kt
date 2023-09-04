//@file:Suppress("CAST_NEVER_SUCCEEDS")

package ru.plumsoftware.weatherforecast.presentation.content.presentation

import android.annotation.SuppressLint
import android.media.Rating
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.nativeads.MediaView
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdEventListener
import com.yandex.mobile.ads.nativeads.NativeAdException
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener
import com.yandex.mobile.ads.nativeads.NativeAdLoader
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration
import com.yandex.mobile.ads.nativeads.NativeAdView
import com.yandex.mobile.ads.nativeads.NativeAdViewBinder
import com.yandex.mobile.ads.nativeads.NativeBulkAdLoadListener
import com.yandex.mobile.ads.nativeads.NativeBulkAdLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.plumsoftware.uicomponents.PlumsoftwareIconPack
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.Weather
import ru.plumsoftware.uicomponents.plumsoftwareiconpack.weather.Sunny
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecast.application.App
import ru.plumsoftware.weatherforecast.data.utilities.logd
import ru.plumsoftware.weatherforecast.domain.models.location.Location
import ru.plumsoftware.weatherforecast.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecast.presentation.content.presentation.components.CityComponent
import ru.plumsoftware.weatherforecast.presentation.content.presentation.components.DetailComponent
import ru.plumsoftware.weatherforecast.presentation.content.presentation.components.HourlyWeatherForecast
import ru.plumsoftware.weatherforecast.presentation.content.presentation.components.TipsComponent
import ru.plumsoftware.weatherforecast.presentation.content.presentation.components.WeatherStatus
import ru.plumsoftware.weatherforecast.presentation.content.store.ContentStore
import ru.plumsoftware.weatherforecast.presentation.content.viewmodel.ContentViewModel

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
    state: ContentStore.State, contentViewModel: ContentViewModel, coroutine: CoroutineScope
) {
    if (state.isAdsLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0x4DFFFFFF)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = ExtensionPaddingValues._10dp, alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            CityComponent(Location(city = state.city, country = state.country),
                dropDownMenuExpanded = state.dropDownState,
                onCLickMoreVert = {
                    contentViewModel.onEvent(
                        event = ContentStore.Intent.DropDownMenuChange(
                            value = state.dropDownState
                        )
                    )
                },
                onCloseDropDownMenu = {
                    contentViewModel.onEvent(
                        event = ContentStore.Intent.DropDownMenuChange(
                            value = state.dropDownState
                        )
                    )
                },
                checkBoxValue = state.checkBoxState,
                onCheckedChange = { value ->
                    contentViewModel.onEvent(
                        event = ContentStore.Intent.CheckBoxChange(
                            value = value
                        )
                    )
                },
                onClickOpenLocation = {
                    contentViewModel.onEvent(
                        event = ContentStore.Intent.OpenLocation
                    )
                },
                onClickOpenSettings = {
                    contentViewModel.onEvent(
                        event = ContentStore.Intent.OpenSettings
                    )
                })
            with(state.owmResponse) {
                WeatherStatus(
                    description = weather[0].description!!,
                    temp = main!!.temp!!.toInt().toString(),
                    tempMax = main!!.tempMax!!.toInt().toString(),
                    tempMin = main!!.tempMin!!.toInt().toString(),
                    tempFeelsLike = main!!.feelsLike!!.toInt().toString(),
                    weatherUnit = "",
                    iconId = weather[0].id!!,
                    base = base!!
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = ExtensionPaddingValues._24dp, alignment = Alignment.Top
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = ExtensionPaddingValues._10dp)
            ) {
                Spacer(modifier = Modifier.height(height = ExtensionPaddingValues._2dp))

//            region::ADS
                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    factory = { context ->
                        val inflate =
                            LayoutInflater.from(context).inflate(R.layout.native_ads, null)
                        inflate
                    },
                    update = { view ->

                        val mNativeAdView = view.findViewById<NativeAdView>(R.id.nativeAdView)
                        val mediaView = view.findViewById<MediaView>(R.id.media)
                        val age = view.findViewById<TextView>(R.id.age)
                        val bodyView = view.findViewById<TextView>(R.id.tvAdvertiser)
                        val call_to_action = view.findViewById<TextView>(R.id.btnVisitSite)
                        val domain = view.findViewById<TextView>(R.id.textViewDomain)
                        val favicon = view.findViewById<ImageView>(R.id.adsPromo)
                        val imageViewFeedback = view.findViewById<ImageView>(R.id.imageViewFeedback)
                        val priceView = view.findViewById<TextView>(R.id.priceView)
                        val storeView = view.findViewById<TextView>(R.id.storeView)
                        val tvHeadline = view.findViewById<TextView>(R.id.tvHeadline)
                        val warning = view.findViewById<TextView>(R.id.textViewWarning)
                        val adsCard = view.findViewById<CardView>(R.id.cardView2)
//                    val rating = view.findViewById<RatingBar>(R.id.rating)

                        val context = App.INSTANCE.applicationContext


//                    region::Load ad
                        coroutine.launch {
                            for (nativeAd in state.adsList) {
                                showAd(
                                    nativeAd,
                                    mNativeAdView,
                                    age,
                                    bodyView,
                                    call_to_action,
                                    domain,
                                    favicon,
                                    imageViewFeedback,
                                    mediaView,
                                    priceView,
                                    storeView,
                                    tvHeadline,
                                    warning
                                )
                            }
                        }
//                    endregion

                    }
                )
//            endregion

//            region::Alerts
                if (state.weatherApiResponse.alerts!!.alert.isNotEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            space = ExtensionPaddingValues._10dp, alignment = Alignment.Top
                        ), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.alerts),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.secondary)
                        )
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    shape = MaterialTheme.shapes.large
                                )
                                .padding(all = ExtensionPaddingValues._14dp)
                        ) {
                            state.weatherApiResponse.alerts!!.alert.forEachIndexed { index, alert ->
                                Text(
                                    text = alert.desc!!,
                                    style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onSecondaryContainer)
                                )
                            }
                        }
                    }
                }
//            endregion

//            region::Tips
                if (state.showTips)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            space = ExtensionPaddingValues._10dp, alignment = Alignment.Top
                        ), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.tips),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.secondary)
                        )
                        TipsComponent(base = state.owmResponse.base!!)
                    }
//            endregion

//            region::Hourly forecast
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = ExtensionPaddingValues._10dp, alignment = Alignment.Top
                    ), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.hourly_weather_forecast),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.secondary)
                    )

                    HourlyWeatherForecast(
                        list = state.weatherApiResponse.forecast!!.forecastday[0].hour,
                        weatherUnits = state.weatherUnits
                    )
                }
//            endregion

//            region::Details
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = ExtensionPaddingValues._10dp, alignment = Alignment.Top
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = ExtensionPaddingValues._14dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.details),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.secondary)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            space = ExtensionPaddingValues._10dp, alignment = Alignment.Top
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(
                                space = ExtensionPaddingValues._10dp,
                                alignment = Alignment.CenterHorizontally
                            )
                        ) {
                            DetailComponent(
                                title = "${state.owmResponse.visibility}м",
                                description = "Видимость", //TODO()
                                pair = Pair(
                                    PlumsoftwareIconPack.Weather.Sunny, Color(0xFFCC9F00)
                                ) // TODO()
                            )

                            DetailComponent(
                                title = "${state.owmResponse.main!!.humidity}%",
                                description = "Влажность",//TODO()
                                pair = Pair(
                                    PlumsoftwareIconPack.Weather.Sunny, Color(70, 92, 204)
                                ) // TODO()
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(
                                space = ExtensionPaddingValues._10dp,
                                alignment = Alignment.CenterHorizontally
                            )
                        ) {
                            DetailComponent(
                                title = calculateUVIndex(state.weatherApiResponse.current!!.uv!!),
                                description = "УВ индекс", //TODO()
                                pair = Pair(
                                    PlumsoftwareIconPack.Weather.Sunny, Color(0xFFCC9F00) // TODO()
                                )
                            )

                            DetailComponent(
                                title = "${state.owmResponse.wind!!.speed!!.toInt()} ${state.windSpeed.windPresentation}",
                                description = windDirectionFull(state.owmResponse.wind!!.deg!!),//TODO()
                                pair = Pair(
                                    PlumsoftwareIconPack.Weather.Sunny, Color(70, 92, 204) // TODO()
                                )
                            )
                        }
                    }
                }
//            endregion

                Spacer(modifier = Modifier.height(height = ExtensionPaddingValues._14dp))
            }
        }
    }
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

private fun windDirectionFull(deg: Int): String {
    val directions = arrayOf(
        "Север",
        "Северо-северо-восток",
        "Северо-восток",
        "Восток-северо-восток",
        "Восток",
        "Восток-юго-восток",
        "Юго-восток",
        "Юго-юго-восток",
        "Юг",
        "Юго-юго-запад",
        "Юго-запад",
        "Запад-юго-запад",
        "Запад",
        "Запад-северо-запад",
        "Северо-запад",
        "Северо-северо-запад"
    )
    val index = ((deg / 22.5) + 0.5).toInt() % 16
    return directions[index]
}

private fun createNativeAdLoader(): NativeAdLoader {
    val nativeAdLoader: NativeAdLoader? = null
    return nativeAdLoader ?: NativeAdLoader(App.INSTANCE.applicationContext).apply {
        setNativeAdLoadListener(object : NativeAdLoadListener {
            override fun onAdLoaded(nativeAd: NativeAd) {

            }

            override fun onAdFailedToLoad(error: AdRequestError) {

            }
        })
    }
}

private fun showAd(
    nativeAd: NativeAd,
    nativeAdView: NativeAdView,
    age: TextView,
    body: TextView,
    callToAction: TextView,
    domain: TextView,
    favicon: ImageView,
    feedback: ImageView,
//    icon: ImageView,
    media: MediaView,
    price: TextView,
//    rating: RatingBar,
//    reviewCount: TextView,
    sponsored: TextView,
    title: TextView,
    warning: TextView


) {
    val nativeAdViewBinder = nativeAd.run {
        NativeAdViewBinder.Builder(nativeAdView)
            .setAgeView(age)
            .setBodyView(body)
            .setCallToActionView(callToAction)
            .setDomainView(domain)
            .setFaviconView(favicon)
            .setFeedbackView(feedback)
//            .setIconView(icon)
            .setMediaView(media)
            .setPriceView(price)
//            .setRatingView(rating)
//            .setReviewCountView(reviewCount)
            .setSponsoredView(sponsored)
            .setTitleView(title)
            .setWarningView(warning)
            .build()
    }

    try {
        nativeAd.bindNativeAd(nativeAdViewBinder)
        nativeAd.setNativeAdEventListener(NativeAdEventLogger())
    } catch (exception: NativeAdException) {
        logd(exception.message.orEmpty())
    }
}

private class NativeAdEventLogger : NativeAdEventListener {

    override fun onAdClicked() {
        // Called when a click is recorded for an ad.
    }

    override fun onLeftApplication() {
        // Called when user is about to leave application (e.g., to go to the browser), as a result of clicking on the ad.
    }

    override fun onReturnedToApplication() {
        // Called when user returned to application after click.
    }

    override fun onImpression(data: ImpressionData?) {
        // Called when an impression is recorded for an ad.
    }
}
//endregion