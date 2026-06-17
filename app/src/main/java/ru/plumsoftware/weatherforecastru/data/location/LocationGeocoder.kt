package ru.plumsoftware.weatherforecastru.data.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationGeocoder(private val context: Context) {

    suspend fun resolveCity(latitude: Double, longitude: Double): String {
        resolveCityWithLocale(latitude, longitude, Locale.getDefault())
            .takeIf { it.isNotBlank() }
            ?.let { return it }

        if (Locale.getDefault().language != "ru") {
            resolveCityWithLocale(latitude, longitude, Locale("ru", "RU"))
                .takeIf { it.isNotBlank() }
                ?.let { return it }
        }

        return ""
    }

    private suspend fun resolveCityWithLocale(
        latitude: Double,
        longitude: Double,
        locale: Locale,
    ): String {
        if (!Geocoder.isPresent()) return ""

        val geocoder = Geocoder(context, locale)
        val addresses = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            awaitAddresses(geocoder, latitude, longitude)
        } else {
            @Suppress("DEPRECATION")
            withContext(Dispatchers.IO) {
                runCatching { geocoder.getFromLocation(latitude, longitude, 1) }
                    .getOrNull()
                    .orEmpty()
            }
        }

        return addresses.firstOrNull()?.let(::extractCityName).orEmpty()
    }

    private suspend fun awaitAddresses(
        geocoder: Geocoder,
        latitude: Double,
        longitude: Double,
    ): List<Address> = suspendCoroutine { continuation ->
        geocoder.getFromLocation(latitude, longitude, 1, object : Geocoder.GeocodeListener {
            override fun onGeocode(addresses: MutableList<Address>) {
                continuation.resume(addresses)
            }

            override fun onError(errorMessage: String?) {
                continuation.resume(emptyList())
            }
        })
    }

    private fun extractCityName(address: Address): String {
        return listOfNotNull(
            address.locality,
            address.subLocality,
            address.subAdminArea,
            address.adminArea,
            address.featureName,
        ).firstOrNull { it.isNotBlank() }
            ?: runCatching { address.getAddressLine(0) }
                .getOrNull()
                .orEmpty()
                .substringBefore(',')
                .trim()
    }
}
