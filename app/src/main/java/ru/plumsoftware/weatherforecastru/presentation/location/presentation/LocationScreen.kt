package ru.plumsoftware.weatherforecastru.presentation.location.presentation

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.application.App
import ru.plumsoftware.weatherforecastru.data.location.LocationHelper
import ru.plumsoftware.weatherforecastru.data.utilities.showToast
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.presentation.location.presentation.components.CityRow
import ru.plumsoftware.weatherforecastru.presentation.location.presentation.components.HistoryCityRow
import ru.plumsoftware.weatherforecastru.presentation.location.store.LocationStore
import ru.plumsoftware.weatherforecastru.presentation.location.viewmodel.LocationViewModel
import ru.plumsoftware.weatherforecastru.presentation.ui.Dimens
import ru.plumsoftware.weatherforecastru.presentation.ui.NavigationBarSpacer
import ru.plumsoftware.weatherforecastru.presentation.ui.medium
import ru.plumsoftware.weatherforecastru.presentation.ui.md_theme_icon_tint
import ru.plumsoftware.weatherforecastru.presentation.ui.regular
import ru.plumsoftware.weatherforecastru.presentation.ui.statusBarTopPadding

@Composable
fun LocationScreen(locationViewModel: LocationViewModel) {
    val state by locationViewModel.state.collectAsState()
    val coroutine = rememberCoroutineScope()

    LaunchedEffect(locationViewModel) {
        locationViewModel.label.collect { label ->
            when (label) {
                is LocationStore.Label.ConfirmLocation -> {
                    coroutine.launch {
                        locationViewModel.save(location = label.location)
                    }
                    locationViewModel.onOutput(
                        LocationViewModel.Output.OpenContentScreen(
                            location = label.location,
                        ),
                    )
                }

                LocationStore.Label.BackButtonClicked -> {
                    locationViewModel.onOutput(LocationViewModel.Output.BackStackClicked)
                }

                is LocationStore.Label.DeleteLocation -> {
                    showToast(App.INSTANCE.applicationContext, label.locationItem.toString())
                    coroutine.launch {
                        locationViewModel.delete(location = label.locationItem)
                    }
                }
            }
        }
    }
    LocationScreen(
        event = locationViewModel::onEvent,
        state = state,
        onDetectLocation = { locationViewModel.detectCurrentLocation() },
        coroutine = coroutine,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationScreen(
    event: (LocationStore.Intent) -> Unit,
    state: LocationStore.State,
    onDetectLocation: suspend () -> Unit,
    coroutine: kotlinx.coroutines.CoroutineScope,
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            coroutine.launch { onDetectLocation() }
        } else {
            event(
                LocationStore.Intent.ShowLocationDetectionDialog(
                    dialog = LocationStore.LocationDetectionDialog.Failed(
                        messageResId = R.string.location_detection_permission_denied,
                    ),
                ),
            )
        }
    }

    fun startLocationDetection() {
        val locationHelper = LocationHelper(context)
        if (!locationHelper.isLocationPermissionGranted()) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
            )
        } else {
            coroutine.launch { onDetectLocation() }
        }
    }

    LaunchedEffect(state.requestAddressFieldFocus) {
        if (state.requestAddressFieldFocus) {
            delay(100)
            state.focusRequester.requestFocus()
            keyboardController?.show()
            event(LocationStore.Intent.RequestAddressFieldFocus(value = false))
        }
    }

    state.locationDetectionDialog?.let { dialog ->
        LocationDetectionDialog(
            dialog = dialog,
            onDismiss = { event(LocationStore.Intent.DismissLocationDetectionDialog) },
            onConfirmDetected = { event(LocationStore.Intent.ConfirmDetectedLocation) },
            onEnterManually = { event(LocationStore.Intent.EnterLocationManually) },
        )
    }

    if (state.showDialog) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = md_theme_icon_tint)
                .fillMaxSize(),
        ) {
            Card(shape = MaterialTheme.shapes.large, modifier = Modifier.wrapContentSize()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = ExtensionPaddingValues._14dp,
                        alignment = Alignment.Bottom,
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(
                            horizontal = ExtensionPaddingValues._16dp,
                            vertical = ExtensionPaddingValues._10dp,
                        ),
                ) {
                    Text(
                        text = "${stringResource(id = R.string.delete_location_hint)} ${state.selectedLocationItem.city}",
                        style = MaterialTheme.typography.bodyMedium.regular(),
                        overflow = TextOverflow.Ellipsis,
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            space = ExtensionPaddingValues._4dp,
                            alignment = Alignment.Bottom,
                        ),
                    ) {
                        TextButton(onClick = {
                            event(LocationStore.Intent.ShowDialog(value = false))
                        }) {
                            Text(
                                text = stringResource(id = R.string.cancel_delete_location),
                                style = MaterialTheme.typography.bodyMedium.regular(),
                            )
                        }

                        TextButton(onClick = {
                            event(LocationStore.Intent.ShowDialog(value = false))
                            event(
                                LocationStore.Intent.DeleteLocation(
                                    locationItem = state.selectedLocationItem,
                                ),
                            )
                        }) {
                            Text(
                                text = stringResource(id = R.string.delete_location),
                                style = MaterialTheme.typography.bodyMedium.regular()
                                    .copy(color = MaterialTheme.colorScheme.error),
                            )
                        }
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .statusBarTopPadding(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                IconButton(onClick = { event(LocationStore.Intent.BackButtonClicked) }) {
                    Icon(Icons.Rounded.ArrowBack, null, tint = MaterialTheme.colorScheme.primary)
                }
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp)
                            .focusRequester(state.focusRequester)
                            .onFocusChanged {
                                if (it.isFocused && state.city.isNotEmpty()) {
                                    event(LocationStore.Intent.CloseIconChange(isVisibleCloseIcon = true))
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(
                            Icons.Outlined.Search,
                            null,
                            modifier = Modifier.height(18.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        BasicTextField(
                            value = state.city,
                            onValueChange = {
                                event(LocationStore.Intent.TextChange(text = it))
                                event(LocationStore.Intent.TextError(isSyntaxError = false))
                                event(LocationStore.Intent.CloseIconChange(isVisibleCloseIcon = it.isNotEmpty()))
                                event(LocationStore.Intent.CountryChange(text = ""))
                            },
                            textStyle = MaterialTheme.typography.bodyMedium.regular().copy(
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    event(LocationStore.Intent.TextError(isSyntaxError = state.city.isEmpty()))
                                    if (state.city.isNotEmpty()) {
                                        event(LocationStore.Intent.SearchButtonClicked(city = state.city))
                                    }
                                },
                            ),
                        )
                        if (state.isVisibleCloseIcon) {
                            IconButton(onClick = {
                                event(LocationStore.Intent.TextChange(text = ""))
                                event(LocationStore.Intent.CloseIconChange(isVisibleCloseIcon = false))
                            }) {
                                Icon(Icons.Rounded.Close, null)
                            }
                        }
                    }
                }
            }

            LaunchedEffect(Unit) { state.focusRequester.requestFocus() }

            LazyColumn(
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    horizontal = Dimens.screenPaddingH,
                ),
            ) {
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        onClick = {
                            if (!state.isDetectingLocation) {
                                startLocationDetection()
                            }
                        },
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (state.isDetectingLocation) {
                                CircularProgressIndicator(
                                    modifier = Modifier.height(18.dp),
                                    strokeWidth = 2.dp,
                                )
                            } else {
                                Icon(
                                    Icons.Outlined.MyLocation,
                                    null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.height(18.dp),
                                )
                            }
                            Text(
                                text = stringResource(R.string.detect_my_location),
                                style = MaterialTheme.typography.bodyMedium.regular(),
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }
                if (state.city.isEmpty() && state.items.isNotEmpty()) {
                    item {
                        Text(
                            "Недавние",
                            style = MaterialTheme.typography.labelSmall.medium(),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 12.dp, bottom = 6.dp),
                        )
                    }
                }
                if (state.city.isEmpty()) {
                    itemsIndexed(state.items) { _, locationItem ->
                        HistoryCityRow(city = locationItem.city) {
                            event(LocationStore.Intent.SearchButtonClicked(city = locationItem.city))
                        }
                    }
                }
                if (state.city.isNotEmpty()) {
                    item {
                        CityRow(
                            city = state.city,
                            onClick = {
                                event(LocationStore.Intent.SearchButtonClicked(city = state.city))
                            },
                        )
                    }
                }
                item { NavigationBarSpacer() }
            }
        }
    }
}

@Composable
private fun LocationDetectionDialog(
    dialog: LocationStore.LocationDetectionDialog,
    onDismiss: () -> Unit,
    onConfirmDetected: () -> Unit,
    onEnterManually: () -> Unit,
) {
    when (dialog) {
        LocationStore.LocationDetectionDialog.VpnBlocked -> {
            AlertDialog(
                onDismissRequest = onDismiss,
                text = {
                    Text(text = stringResource(R.string.location_detection_vpn_message))
                },
                confirmButton = {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(R.string.ok))
                    }
                },
            )
        }

        is LocationStore.LocationDetectionDialog.Success -> {
            AlertDialog(
                onDismissRequest = onDismiss,
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = stringResource(
                                R.string.location_detection_success_message,
                                dialog.city,
                            ),
                        )
                        Text(
                            text = stringResource(R.string.location_detection_success_hint),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = onConfirmDetected) {
                        Text(stringResource(R.string.location_use_detected))
                    }
                },
                dismissButton = {
                    TextButton(onClick = onEnterManually) {
                        Text(stringResource(R.string.location_enter_manually))
                    }
                },
            )
        }

        is LocationStore.LocationDetectionDialog.Failed -> {
            AlertDialog(
                onDismissRequest = onDismiss,
                text = {
                    Text(text = stringResource(dialog.messageResId))
                },
                confirmButton = {
                    TextButton(onClick = onEnterManually) {
                        Text(stringResource(R.string.location_enter_manually))
                    }
                },
                dismissButton = {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(R.string.ok))
                    }
                },
            )
        }
    }
}
