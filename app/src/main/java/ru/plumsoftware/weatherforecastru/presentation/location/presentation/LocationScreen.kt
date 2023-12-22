package ru.plumsoftware.weatherforecastru.presentation.location.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.application.App
import ru.plumsoftware.weatherforecastru.data.utilities.showToast
import ru.plumsoftware.weatherforecastru.material.extensions.ExtensionPaddingValues
import ru.plumsoftware.weatherforecastru.material.components.TopBar
import ru.plumsoftware.weatherforecastru.presentation.location.presentation.components.LocationItem
import ru.plumsoftware.weatherforecastru.presentation.location.store.LocationStore
import ru.plumsoftware.weatherforecastru.presentation.location.viewmodel.LocationViewModel
import ru.plumsoftware.weatherforecastru.presentation.ui.md_theme_icon_tint

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
                            location = label.location
                        )
                    )
                }

                LocationStore.Label.BackButtonClicked -> {
                    locationViewModel.onOutput(LocationViewModel.Output.BackStackClicked)
                }

                is LocationStore.Label.DeleteLocation -> {
                    showToast(App.INSTANCE.applicationContext, label.locationItem.toString())
                    locationViewModel.delete(location = label.locationItem)
                }
            }
        }
    }
    LocationScreen(
        event = locationViewModel::onEvent,
        state = state
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationScreen(
    event: (LocationStore.Intent) -> Unit,
    state: LocationStore.State
) {
    if (state.showDialog)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = md_theme_icon_tint)
                .fillMaxSize()
        ) {
            Card(shape = MaterialTheme.shapes.large, modifier = Modifier.wrapContentSize()) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = ExtensionPaddingValues._14dp,
                        alignment = Alignment.Bottom
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(
                            horizontal = ExtensionPaddingValues._16dp,
                            vertical = ExtensionPaddingValues._10dp
                        )
                ) {
                    Text(
                        text = "${stringResource(id = R.string.delete_location_hint)} ${state.selectedLocationItem.city}",
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            space = ExtensionPaddingValues._4dp,
                            alignment = Alignment.Bottom
                        )
                    ) {
                        TextButton(onClick = {
                            event(LocationStore.Intent.ShowDialog(value = false))
                        }) {
                            Text(
                                text = stringResource(id = R.string.cancel_delete_location),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        TextButton(onClick = {
                            event(LocationStore.Intent.ShowDialog(value = false))
                            event(
                                LocationStore.Intent.DeleteLocation(
                                    locationItem = state.selectedLocationItem
                                )
                            )
                        }) {
                            Text(
                                text = stringResource(id = R.string.delete_location),
                                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.error)
                            )
                        }
                    }
                }
            }
        }
    else
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(all = ExtensionPaddingValues._24dp)
        ) {
            with(ExtensionPaddingValues) {
                TopBar(
                    showBack = true,
                    textResId = R.string.location,
                    onBackClick = { event(LocationStore.Intent.BackButtonClicked) })
                Spacer(modifier = Modifier.height(height = _10dp))
                OutlinedTextField(
                    value = state.city,
                    textStyle = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .focusRequester(state.focusRequester)
                        .onFocusChanged {
                            if (it.isFocused) {
                                if (state.city.isNotEmpty())
                                    event(LocationStore.Intent.CloseIconChange(isVisibleCloseIcon = true))
                            }
                        },
                    onValueChange = {
                        with(it) {
                            event(LocationStore.Intent.TextChange(text = this@with))
                            event(LocationStore.Intent.TextError(isSyntaxError = false))
                            event(LocationStore.Intent.CloseIconChange(isVisibleCloseIcon = this@with.isNotEmpty()))
                            event(LocationStore.Intent.CountryChange(text = ""))
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    visualTransformation = VisualTransformation.None,
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            event(LocationStore.Intent.TextError(isSyntaxError = state.city.isEmpty()))
                            if (state.city.isNotEmpty()) {
                                event(LocationStore.Intent.SearchButtonClicked(city = state.city))
                            }
                        }
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = .3f),
                        focusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = .3f),
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = .3f),
                    ),
                    shape = RoundedCornerShape(100.dp),
                    isError = state.isSyntaxError,
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = App.INSTANCE.getString(R.string.search_icon_description),
                            tint = if (state.isSyntaxError) MaterialTheme.colorScheme.error else LocalContentColor.current
                        )
                    },
                    placeholder = {
                        Text(
                            text = App.INSTANCE.getString(R.string.location_text_hint),
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.secondary),
                        )
                    },
                    trailingIcon = {
                        if (state.isVisibleCloseIcon) {
                            IconButton(onClick = {
                                event(LocationStore.Intent.TextChange(text = ""))
                                event(LocationStore.Intent.CloseIconChange(isVisibleCloseIcon = false))
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = App.INSTANCE.getString(R.string.clear_icon_description),
                                )
                            }
                        }
                    }
                )
            }

            LaunchedEffect(Unit) {
                state.focusRequester.requestFocus()
            }

            Spacer(modifier = Modifier.height(height = ExtensionPaddingValues._14dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            {
                itemsIndexed(state.items) { index, locationItem ->
                    LocationItem(
                        city = locationItem.city,
                        onClick = { selectedLocation ->
                            event(LocationStore.Intent.SearchButtonClicked(city = selectedLocation.city))
                            event(LocationStore.Intent.CountryChange(text = selectedLocation.country.ifEmpty { "" }))
                        },
                        onLongClick = {
//                            event(LocationStore.Intent.ShowDialog(value = true))
//                            event(
//                                LocationStore.Intent.ChangeSelectedLocationItem(
//                                    locationItem = ru.plumsoftware.weatherforecast.data.models.location.LocationItem(
//                                        id = index,
//                                        city = locationItem.city
//                                    )
//                                )
//                            )
                        }
                    )
                }
            }
        }
}