package ru.plumsoftware.weatherforecastru.widgets.utilites

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.Gson
import io.ktor.http.HttpStatusCode
import io.ktor.util.date.GMTDate
import ru.plumsoftware.uicomponents.R
import ru.plumsoftware.weatherforecastru.application.App
import ru.plumsoftware.weatherforecastru.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.weatherforecastru.domain.remote.dto.either.WeatherEither
import ru.plumsoftware.weatherforecastru.domain.storage.HttpClientStorage

private inline fun <reified T> convertStringToJson(jsonString: String): T =
    Gson().fromJson(jsonString, T::class.java)

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

suspend fun doHttpResponse(
    httpClientStorage: HttpClientStorage
): Pair<Pair<HttpStatusCode, HttpStatusCode>, Pair<OwmResponse, WeatherApiResponse>> {

    val checkInternetConnection =
        checkInternetConnection(context = App.INSTANCE.applicationContext)

    if (checkInternetConnection) {
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
        return Pair(
            first = Pair(first = HttpStatusCode(-1, ""), second = HttpStatusCode(-1, "")),
            second = Pair(first = OwmResponse(), second = WeatherApiResponse())
        )
    }
}

fun badIconToGoodIcon(icon: Int): Int {
    return when (icon) {
        in 200..232 -> {
            UI.weather_icons[14]
        }

        in 300..321 -> {
            UI.weather_icons[16]
        }

        in 500..531 -> {
            UI.weather_icons[17]
        }

        in 600..621 -> {
            UI.weather_icons[19]
        }

        in 700..721 -> {
            UI.weather_icons[12]
        }

        731 -> {
            UI.weather_icons[8]
        }

        in 741..751 -> {
            UI.weather_icons[9]
        }

        in 761..771 -> {
            UI.weather_icons[8]
        }

        781 -> {
            UI.weather_icons[13]
        }

        800 -> {
            UI.weather_icons[0]
        }

        801 -> {
            UI.weather_icons[2]
        }

        802 -> {
            UI.weather_icons[4]
        }

        in 803..804 -> {
            UI.weather_icons[6]
        }

        else -> {
            UI.weather_icons[0]
        }
    }
}

private object UI {
    val weather_icons = listOf<Int>(
        R.drawable.clear_day,
        R.drawable.dark_mode_48px,
        R.drawable.cloudy_1_day,
        R.drawable.cloudy_1_night,
        R.drawable.cloudy_2_day,
        R.drawable.cloudy_2_night,
        R.drawable.cloudy_3_day,
        R.drawable.cloudy_3_night,
        R.drawable.dust,
        R.drawable.fog_day,
        R.drawable.fog_night,
        R.drawable.haze_day,
        R.drawable.haze_night,
        R.drawable.hurricane,
        R.drawable.isolated_thunderstorms_day,
        R.drawable.isolated_thunderstorms_night,
        R.drawable.rainy_3,
        R.drawable.rainy_3_day,
        R.drawable.rainy_3_night,
        R.drawable.snowy_3_day,
        R.drawable.snowy_3_night
    )
}