package ru.plumsoftware.weatherforecastru.presentation.widgetconfig.presentation.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.application.permissions.AppPermissionsHelper
import ru.plumsoftware.weatherforecastru.utilites.loadWallpaperImage

@Composable
fun WallpaperBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val context = LocalContext.current
    var refreshKey by remember { mutableIntStateOf(0) }
    var wallpaper by remember { mutableStateOf<ImageBitmap?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) {
        refreshKey++
    }

    LaunchedEffect(Unit) {
        if (AppPermissionsHelper.needsWallpaperPermission(context)) {
            permissionLauncher.launch(
                AppPermissionsHelper.wallpaperPermissionsToRequest().toTypedArray(),
            )
        }
    }

    LaunchedEffect(refreshKey) {
        wallpaper = loadWallpaperImage(context)
    }

    Box(modifier = modifier) {
        if (wallpaper != null) {
            Image(
                bitmap = wallpaper!!,
                contentDescription = stringResource(R.string.wallpaper_image_hint),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.08f)),
        )

        content()
    }
}
