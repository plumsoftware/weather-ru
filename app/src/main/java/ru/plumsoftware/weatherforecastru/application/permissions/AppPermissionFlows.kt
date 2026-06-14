package ru.plumsoftware.weatherforecastru.application.permissions

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

private enum class EntryPermissionStep {
    NOTIFICATION,
    BATTERY,
    STORAGE,
}

private enum class NotificationSetupStep {
    NOTIFICATION,
    BATTERY,
}

@Composable
fun EntryPermissionsFlow(
    activity: Activity,
    active: Boolean,
    onFinished: () -> Unit,
) {
    var step by remember { mutableStateOf<EntryPermissionStep?>(null) }

    val notificationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) {
        step = EntryPermissionStep.BATTERY
    }

    val batteryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) {
        step = EntryPermissionStep.STORAGE
    }

    val storageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) {
        step = null
        onFinished()
    }

    LaunchedEffect(active) {
        if (active) {
            step = EntryPermissionStep.NOTIFICATION
        }
    }

    LaunchedEffect(step) {
        when (step) {
            EntryPermissionStep.NOTIFICATION -> {
                if (AppPermissionsHelper.needsNotificationPermission(activity)) {
                    notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    step = EntryPermissionStep.BATTERY
                }
            }

            EntryPermissionStep.BATTERY -> {
                val batteryIntent = AppPermissionsHelper.batteryOptimizationIntent(activity)
                if (batteryIntent != null && AppPermissionsHelper.needsBatteryOptimizationExemption(activity)) {
                    batteryLauncher.launch(batteryIntent)
                } else {
                    step = EntryPermissionStep.STORAGE
                }
            }

            EntryPermissionStep.STORAGE -> {
                val permissions = AppPermissionsHelper.storagePermissionsToRequest()
                    .filter { permission ->
                        androidx.core.content.ContextCompat.checkSelfPermission(
                            activity,
                            permission,
                        ) != android.content.pm.PackageManager.PERMISSION_GRANTED
                    }
                if (permissions.isNotEmpty()) {
                    storageLauncher.launch(permissions.toTypedArray())
                } else {
                    step = null
                    onFinished()
                }
            }

            null -> Unit
        }
    }
}

@Composable
fun rememberNotificationSetupPermissionHandler(
    activity: Activity,
    onComplete: () -> Unit,
): () -> Unit {
    var step by remember { mutableStateOf<NotificationSetupStep?>(null) }
    var pendingComplete by remember { mutableStateOf(false) }

    val notificationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) {
        step = NotificationSetupStep.BATTERY
    }

    val batteryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) {
        step = null
        if (pendingComplete) {
            pendingComplete = false
            onComplete()
        }
    }

    LaunchedEffect(step) {
        when (step) {
            NotificationSetupStep.NOTIFICATION -> {
                if (AppPermissionsHelper.needsNotificationPermission(activity)) {
                    notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    step = NotificationSetupStep.BATTERY
                }
            }

            NotificationSetupStep.BATTERY -> {
                val batteryIntent = AppPermissionsHelper.batteryOptimizationIntent(activity)
                if (batteryIntent != null && AppPermissionsHelper.needsBatteryOptimizationExemption(activity)) {
                    batteryLauncher.launch(batteryIntent)
                } else {
                    step = null
                    if (pendingComplete) {
                        pendingComplete = false
                        onComplete()
                    }
                }
            }

            null -> Unit
        }
    }

    return remember(onComplete) {
        {
            if (AppPermissionsHelper.needsNotificationOrBattery(activity)) {
                pendingComplete = true
                step = NotificationSetupStep.NOTIFICATION
            } else {
                onComplete()
            }
        }
    }
}
