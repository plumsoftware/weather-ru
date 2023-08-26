package ru.plumsoftware.weatherforecast.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import ru.plumsoftware.weatherforecast.data.utilities.logd
import java.io.IOException
import java.util.Locale

class LocationHelper(private val context: Context) {

    private val locationManager: LocationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    fun isLocationPermissionGranted(): Boolean {
        val result = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

        logd("LOCATION PERMISSION: ${if (result) "GRANTED" else "DENIED"}")
        return result
    }

    fun isLocationEnabled(): Boolean {
        val result = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        logd("LOCATION ENABLED: ${result.toString()}")
        return result
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onSuccess: (latitude: Double, longitude: Double, city: String?, country: String?) -> Unit) {
        val location =
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    ?: locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        location?.let {
            val cityAndCountry = getCityAndCountry(it.latitude, it.longitude)
            onSuccess(it.latitude, it.longitude, cityAndCountry.first, cityAndCountry.second)
        }
    }

    private fun getCityAndCountry(latitude: Double, longitude: Double): Pair<String?, String?> {
        val geocoder = Geocoder(context, Locale.getDefault())
        var city: String? = null
        var country: String? = null

        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses!!.isNotEmpty()) {
                city = addresses[0].locality
                country = addresses[0].countryName

                logd("CITY: ${city}")
                logd("COUNTRY: ${country}")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Pair(city, country)
    }
}