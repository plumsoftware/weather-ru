package ru.plumsoftware.weatherforecast.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import ru.plumsoftware.weatherforecast.data.utilities.logd

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

    fun getCurrentLocation(onSuccess: (latitude: Double, longitude: Double) -> Unit) {
        val location =
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            } else {
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    ?: locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
        location?.let {
            onSuccess(it.latitude, it.longitude)
        }
    }
}