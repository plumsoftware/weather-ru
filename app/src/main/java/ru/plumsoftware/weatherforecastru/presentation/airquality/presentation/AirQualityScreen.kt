package ru.plumsoftware.weatherforecastru.presentation.airquality.presentation

import android.view.LayoutInflater
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
//import co.yml.charts.common.model.PlotType
//import co.yml.charts.ui.piechart.models.PieChartConfig
//import co.yml.charts.ui.piechart.models.PieChartData
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecast.BuildConfig
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.application.App
import ru.plumsoftware.weatherforecastru.material.components.TopBar
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.presentation.airquality.store.AirQualityStore
import ru.plumsoftware.weatherforecastru.presentation.airquality.viewmodel.AirQualityViewModel

@Composable
fun AirQualityScreen(airQualityViewModel: AirQualityViewModel) {
    val state by airQualityViewModel.state.collectAsState()

    LaunchedEffect(airQualityViewModel) {
        airQualityViewModel.label.collect { label ->
            when (label) {
                AirQualityStore.Label.BackButtonClicked -> {
                    airQualityViewModel.onOutput(
                        AirQualityViewModel.Output.OpenContentScreen
                    )
                }
            }
        }
    }
    AirQualityScreen(state = state, event = airQualityViewModel::onEvent)
}

@Composable
private fun AirQualityScreen(
    state: AirQualityStore.State,
    event: (AirQualityStore.Intent) -> Unit
) {

    val randomColors = mutableListOf<Int>()

    repeat(10) {
        randomColors.add(generateRandomColor())
    }

    // Создание 10 переменных и присвоение им сгенерированных цветов
    val color1 = Color(0xFFDDED37)
    val color2 = Color(0xFFFFDEAD)
    val color3 = Color(0xFFE1E0FF)

    val color4 = Color(0xFF78F8DE)
    val color5 = Color(0xFFB8F397)
    val color6 = Color(0xFFE9DDFF)

    // Создание 10 цветов, темнее первых 10, но с тем же оттенком
    val darkerColor1 = Color(0xFF1A1D00)
    val darkerColor2 = Color(0xFF281900)
    val darkerColor3 = Color(0xFF0A0664)

    val darkerColor4 = Color(0xFF00201A)
    val darkerColor5 = Color(0xFF072100)
    val darkerColor6 = Color(0xFF22005C)

    val colorPairs = listOf(
        Pair(color1, darkerColor1),
        Pair(color2, darkerColor2),
        Pair(color3, darkerColor3),
        Pair(color4, darkerColor4),
        Pair(color5, darkerColor5),
        Pair(color6, darkerColor6)
    )

    val coroutine = rememberCoroutineScope()

    val sum = with(state.airQuality) {
        co!!.toFloat() + no2!!.toFloat() + o3!!.toFloat() + so2!!.toFloat() + pm25!!.toFloat() + pm10!!.toFloat()
    }


    val pieChartData = with(state.airQuality) {
        listOf<Pair<String, Float>>(
            Pair("CO", (co!!.toFloat() * 100) / sum),
            Pair("NO₂", (no2!!.toFloat() * 100) / sum),
            Pair("O₃", (o3!!.toFloat() * 100) / sum),
            Pair("SO₂", (so2!!.toFloat() * 100) / sum),
            Pair("PM₂₅", (pm25!!.toFloat() * 100) / sum),
            Pair("PM₁₀", (pm10!!.toFloat() * 100) / sum)
        )
//        PieChartData(
//            slices = listOf(
//                PieChartData.Slice(
//                    label = ,
//                    value = ,
//                    color = color1
//                ),
//                PieChartData.Slice(
//                    label = "",
//                    value = ,
//                    color = color2
//                ),
//                PieChartData.Slice(
//                    label = "",
//                    value = ,
//                    color = color3
//                ),
//                PieChartData.Slice(
//                    label = "",
//                    value = ,
//                    color = color4
//                ),
//                PieChartData.Slice(
//                    label = "",
//                    value = ,
//                    color = color5
//                ),
//                PieChartData.Slice(
//                    label = "",
//                    value = ,
//                    color = color6
//                )
//            ),
//            plotType = PlotType.Pie
//        )
    }

//    val pieChartConfig = PieChartConfig(
//        isAnimationEnable = true,
//        showSliceLabels = false,
//        activeSliceAlpha = 0.5f,
//        animationDuration = 800
//    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        with(ExtensionPaddingValues) {
//                Back
            Box(modifier = Modifier.padding(all = _18dp)) {
                TopBar(
                    showBack = true,
                    textResId = R.string.air_quality,
                    onBackClick = {
                        event(AirQualityStore.Intent.BackButtonClicked)
                    })
            }
            Spacer(modifier = Modifier.height(height = _14dp))
        }

//                Content
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = ExtensionPaddingValues._24dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
//                .verticalScroll(state = rememberScrollState())
                .weight(weight = 1f, fill = false)
                .padding(horizontal = ExtensionPaddingValues._14dp)
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(minSize = 90.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalItemSpacing = ExtensionPaddingValues._10dp,
                horizontalArrangement = Arrangement.spacedBy(
                    space = ExtensionPaddingValues._10dp,
                    alignment = Alignment.Start
                )
            ) {
                itemsIndexed(pieChartData) { index, item ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = colorPairs[index].first,
                            contentColor = colorPairs[index].second
                        ),
                        modifier = Modifier
                            .wrapContentHeight()
                            .widthIn(min = 200.dp)
                            .width(width = (item.second).toInt().dp)
                            .padding()
                    ) {
                        Text(
                            text = "${item.first} ${item.second.toInt()}%",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(all = ExtensionPaddingValues._14dp)
                        )
                    }
                }
            }
        }

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            factory = { context ->
                val inflate =
                    LayoutInflater.from(context).inflate(R.layout.banner_ads, null)
                inflate
            },
            update = { view ->

                val bannerAd: BannerAdView? = view.findViewById(R.id.banner)
                val context = App.INSTANCE.applicationContext

                bannerAd!!.setAdSize(BannerAdSize.fixedSize(context, 300, 100))
                bannerAd.setAdUnitId(BuildConfig.BANNER_ADS_ID)
                bannerAd.setBannerAdEventListener(object : BannerAdEventListener {
                    override fun onAdLoaded() {

                    }

                    override fun onAdFailedToLoad(adRequestError: AdRequestError) {

                    }

                    override fun onAdClicked() {

                    }

                    override fun onLeftApplication() {

                    }

                    override fun onReturnedToApplication() {

                    }

                    override fun onImpression(impressionData: ImpressionData?) {

                    }
                })
                bannerAd.loadAd(
                    AdRequest.Builder()

                        .build()
                )


//                    region::Load ad
                coroutine.launch {

                }
//                    endregion

            }
        )
    }
}

private fun generateRandomColor(): Int {
    return (0x000000..0xFFFFFF).random()
}

private fun makeDarkerColor(color: Int): Int {
    val red = (color shr 16) and 0xFF
    val green = (color shr 8) and 0xFF
    val blue = color and 0xFF

    val darkerRed = (red * 0.8).toInt()
    val darkerGreen = (green * 0.8).toInt()
    val darkerBlue = (blue * 0.8).toInt()

    return (darkerRed shl 16) or (darkerGreen shl 8) or darkerBlue
}