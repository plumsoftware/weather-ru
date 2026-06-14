package ru.plumsoftware.weatherforecastru.application.permissions

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.core.content.ContextCompat

object AppPermissionsHelper {

    fun needsNotificationPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return false
        return !isPermissionGranted(context, Manifest.permission.POST_NOTIFICATIONS)
    }

    fun needsBatteryOptimizationExemption(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return !powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }

    fun needsNotificationOrBattery(context: Context): Boolean =
        needsNotificationPermission(context) || needsBatteryOptimizationExemption(context)

    fun storagePermissionsToRequest(): List<String> = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        else -> {
            listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        }
    }

    fun needsStoragePermissions(context: Context): Boolean =
        storagePermissionsToRequest().any { permission ->
            !isPermissionGranted(context, permission)
        }

    fun needsEntryPermissions(context: Context): Boolean =
        needsNotificationPermission(context) ||
            needsBatteryOptimizationExemption(context) ||
            needsStoragePermissions(context)

    fun batteryOptimizationIntent(context: Context): Intent? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return null
        return Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
            data = Uri.parse("package:${context.packageName}")
        }
    }

    private fun isPermissionGranted(context: Context, permission: String): Boolean =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}
