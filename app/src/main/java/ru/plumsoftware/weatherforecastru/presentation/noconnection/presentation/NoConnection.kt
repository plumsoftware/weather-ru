package ru.plumsoftware.weatherforecastru.presentation.noconnection.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionSize
import ru.plumsoftware.weatherforecastru.presentation.main.store.MainStore
import ru.plumsoftware.weatherforecastru.presentation.main.viewmodel.MainViewModel
import ru.plumsoftware.weatherforecastru.presentation.noconnection.store.NoConnectionStore
import ru.plumsoftware.weatherforecastru.presentation.noconnection.viewmodel.NoConnectionViewModel

@Composable
fun NoConnection(noConnectionViewModel: NoConnectionViewModel) {
    val state by noConnectionViewModel.state.collectAsState()

    LaunchedEffect(noConnectionViewModel) {
        noConnectionViewModel.label.collect { label ->
            when (label) {
                NoConnectionStore.Label.TryInternetConnection -> noConnectionViewModel.onOutput(
                    NoConnectionViewModel.Output.TryInternetConnection
                )
            }
        }
    }

    NoConnection(
        event = noConnectionViewModel::onEvent,
        state = state
    )
}

@Composable
private fun NoConnection(
    event: (NoConnectionStore.Intent) -> Unit,
    state: NoConnectionStore.State
) {
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

        IconButton(onClick = { event(NoConnectionStore.Intent.TryInternetConnection) }) {
            Icon(
                imageVector = Icons.Rounded.Refresh,
                contentDescription = stringResource(id = R.string.refresh_icon_hint)
            )
        }
    }
}