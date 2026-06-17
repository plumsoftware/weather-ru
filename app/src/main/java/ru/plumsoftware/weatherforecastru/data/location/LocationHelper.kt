package ru.plumsoftware.weatherforecastru.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException
import java.util.Locale
import kotlin.coroutines.resume

class LocationHelper(private val context: Context) {

    private val locationManager: LocationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private val mainHandler = Handler(Looper.getMainLooper())

    fun isLocationPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED
    }

    fun isLocationEnabled(): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onSuccess: (latitude: Double, longitude: Double, city: String?, country: String?) -> Unit) {
        if (!isLocationPermissionGranted()) return

        val cachedLocation = getBestLastKnownLocation()
        if (cachedLocation != null) {
            val cityAndCountry = getCityAndCountry(cachedLocation.latitude, cachedLocation.longitude)
            onSuccess(
                cachedLocation.latitude,
                cachedLocation.longitude,
                cityAndCountry.first,
                cityAndCountry.second,
            )
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun awaitCurrentLocation(timeoutMs: Long = 15_000L): Location? {
        if (!isLocationPermissionGranted() || !isLocationEnabled()) return null

        return suspendCancellableCoroutine { continuation ->
            val cachedLocation = getBestLastKnownLocation()
            if (cachedLocation != null && isRecent(cachedLocation)) {
                continuation.resume(cachedLocation)
                return@suspendCancellableCoroutine
            }

            val providers = listOf(
                LocationManager.GPS_PROVIDER,
                LocationManager.NETWORK_PROVIDER,
            ).filter { locationManager.isProviderEnabled(it) }

            if (providers.isEmpty()) {
                continuation.resume(cachedLocation)
                return@suspendCancellableCoroutine
            }

            var resolved = false
            val listener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    if (resolved) return
                    resolved = true
                    runCatching { locationManager.removeUpdates(this) }
                    mainHandler.removeCallbacksAndMessages(null)
                    if (continuation.isActive) {
                        continuation.resume(location)
                    }
                }
            }

            providers.forEach { provider ->
                runCatching {
                    locationManager.requestLocationUpdates(
                        provider,
                        0L,
                        0f,
                        listener,
                        Looper.getMainLooper(),
                    )
                }
            }

            mainHandler.postDelayed({
                if (resolved) return@postDelayed
                resolved = true
                runCatching { locationManager.removeUpdates(listener) }
                if (continuation.isActive) {
                    continuation.resume(cachedLocation)
                }
            }, timeoutMs)

            continuation.invokeOnCancellation {
                runCatching { locationManager.removeUpdates(listener) }
                mainHandler.removeCallbacksAndMessages(null)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getBestLastKnownLocation(): Location? {
        val providers = listOf(
            LocationManager.GPS_PROVIDER,
            LocationManager.NETWORK_PROVIDER,
        )
        return providers.mapNotNull { provider ->
            runCatching { locationManager.getLastKnownLocation(provider) }.getOrNull()
        }.maxByOrNull { location -> location.time }
    }

    private fun isRecent(location: Location): Boolean {
        return System.currentTimeMillis() - location.time <= RECENT_LOCATION_MAX_AGE_MS
    }

    private fun getCityAndCountry(latitude: Double, longitude: Double): Pair<String?, String?> {
        val geocoder = Geocoder(context, Locale.getDefault())
        var city: String? = null

        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                city = addresses[0].locality
            }
        } catch (_: IOException) {
        }

        return Pair(city, null)
    }

    private companion object {
        const val RECENT_LOCATION_MAX_AGE_MS = 60_000L
    }
}
