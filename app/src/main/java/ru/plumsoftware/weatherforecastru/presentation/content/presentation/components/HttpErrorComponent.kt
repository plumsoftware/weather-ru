package ru.plumsoftware.weatherforecastru.presentation.content.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues

@Composable
fun HttpErrorComponent(httpCode: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = ExtensionPaddingValues._16dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Rounded.Warning,
            contentDescription = stringResource(id = R.string.error_desc),
            tint = MaterialTheme.colorScheme.error
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                space = ExtensionPaddingValues._8dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.error_desc))
            Text(text = "${stringResource(id = R.string.error_code)} $httpCode")
        }
    }
}