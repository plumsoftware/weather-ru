package ru.plumsoftware.weatherforecastru.widget.utilites

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.google.gson.Gson
import io.ktor.http.HttpStatusCode
import io.ktor.util.date.GMTDate
import ru.plumsoftware.uicomponents.R
import ru.plumsoftware.weatherforecastru.application.App
import ru.plumsoftware.weatherforecastru.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.WeatherApiResponse
import ru.plumsoftware.weatherforecastru.data.utilities.showToast
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

fun makeColorDarker(color: Color, amount: Float = 0.6f): Color {
    val red = color.red
    val green = color.green
    val blue = color.blue

    val darkerRed = (red * amount).coerceIn(0f, 1f)
    val darkerGreen = (green * amount).coerceIn(0f, 1f)
    val darkerBlue = (blue * amount).coerceIn(0f, 1f)

    val resColor = Color(red = darkerRed, green = darkerGreen, blue = darkerBlue)

    return if (isDarkColor(resColor)) {
        val c = lightenColor(color = color.value.toInt())
        Color(red = c.red, green = c.green, blue = c.blue, alpha = 140)
    } else
        resColor
}

fun darkerColor(color: Color): Color {
    val red = (color.red * 255).toInt()
    val green = (color.green * 255).toInt()
    val blue = (color.blue * 255).toInt()

    val newRed = (red * 0.25).toInt()
    val newGreen = (green * 0.25).toInt()
    val newBlue = (blue * 0.25).toInt()

    val resColor = Color(
        red = newRed / 255f,
        green = newGreen / 255f,
        blue = newBlue / 255f,
        alpha = color.alpha
    )
    return if (isDarkColor(resColor)) {
        val lightenColor: Int = lightenColor(color = color.value.toInt())
        Color(color = lightenColor)
    } else
        resColor
}

private fun lightenColor(color: Int): Int {
    val red = (color shr 16) and 0xFF
    val green = (color shr 8) and 0xFF
    val blue = color and 0xFF

    val lightRed = red + ((255 - red) / 2)
    val lightGreen = green + ((255 - green) / 2)
    val lightBlue = blue + ((255 - blue) / 2)

    return (0xFF shl 24) or (lightRed shl 16) or (lightGreen shl 8) or lightBlue
}

private fun isDarkColor(color: Color): Boolean {
    val luminance = (0.299 * color.red + 0.587 * color.green + 0.114 * color.blue)
    return luminance < 0.12
}
