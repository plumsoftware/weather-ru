package ru.plumsoftware.weatherforecastru.presentation.noconnection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionSize

@Composable
fun NoConnection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            ExtensionPaddingValues._24dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            modifier = Modifier.size(size = ExtensionSize.IconSize._64dp),
            imageVector = Icons.Rounded.Warning,
            contentDescription = stringResource(id = R.string.error_desc),
            tint = MaterialTheme.colorScheme.error
        )

        Text(
            text = stringResource(id = R.string.error_desc),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}